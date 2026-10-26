package com.gestionDonaton.gestion_logistica.service;

import com.gestionDonaton.gestion_logistica.client.DonacionClient;
import com.gestionDonaton.gestion_logistica.dto.DonacionDTO;
import com.gestionDonaton.gestion_logistica.dto.DonacionEvent;
import com.gestionDonaton.gestion_logistica.dto.EnvioRequestDTO;
import com.gestionDonaton.gestion_logistica.dto.EnvioResponseDTO;
import com.gestionDonaton.gestion_logistica.entity.Envio;
import com.gestionDonaton.gestion_logistica.factory.EnvioFactory;
import com.gestionDonaton.gestion_logistica.repository.EnvioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnvioService {

    private final EnvioRepository envioRepository;
    private final EnvioFactory envioFactory;
    private final DonacionClient donacionClient;

    // Constructor explícito para inyección de Spring (Reemplaza a @RequiredArgsConstructor)
    @Autowired
    public EnvioService(EnvioRepository envioRepository, EnvioFactory envioFactory, DonacionClient donacionClient) {
        this.envioRepository = envioRepository;
        this.envioFactory = envioFactory;
        this.donacionClient = donacionClient;
    }

    public EnvioResponseDTO planificarEnvio(EnvioRequestDTO request) {
        Envio nuevoEnvio = envioFactory.crearEnvio(request);
        return mapToResponseDTO(envioRepository.save(nuevoEnvio));
    }

    /**
     * Procesa un evento asíncrono recibido desde RabbitMQ (cola.documentos).
     */
    public EnvioResponseDTO procesarEventoRabbitMQ(DonacionEvent evento) {
        DonacionDTO donacion = new DonacionDTO();
        donacion.setId(evento.idDonacion());
        donacion.setNombreObjeto(evento.donante());

        EnvioRequestDTO request = autoDeterminarLogistica(donacion);
        Envio envio = envioFactory.crearEnvio(request);

        Envio guardado = envioRepository.save(envio);
        System.out.println("💾 [Logística DB] Envío registrado correctamente con ID: " + guardado.getId());
        return mapToResponseDTO(guardado);
    }

    public List<EnvioResponseDTO> listarEnvios() {
        return envioRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EnvioResponseDTO> procesarDonacionesAutomaticas(String palabraClave) {
        return donacionClient.buscarPorPalabra(palabraClave).stream()
                .map(donacion -> {
                    EnvioRequestDTO request = autoDeterminarLogistica(donacion);
                    Envio envio = envioFactory.crearEnvio(request);
                    return mapToResponseDTO(envioRepository.save(envio));
                })
                .collect(Collectors.toList());
    }

    public EnvioResponseDTO actualizarEstado(Long id, String nuevoEstado) {
        Envio envio = envioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Envío no encontrado"));

        envio.setEstado(nuevoEstado.toUpperCase());

        if ("ENTREGADO".equalsIgnoreCase(nuevoEstado)) {
            try {
                donacionClient.completarDonacion(envio.getDonacionId());
                System.out.println("✅ Sincronización exitosa con Microservicio Donaciones.");
            } catch (Exception e) {
                System.err.println("⚠️ Error de red con Donaciones: " + e.getMessage());
            }
        }

        return mapToResponseDTO(envioRepository.save(envio));
    }

    private EnvioRequestDTO autoDeterminarLogistica(DonacionDTO donacion) {
        String objeto = (donacion.getNombreObjeto() != null) ? donacion.getNombreObjeto().toLowerCase() : "";

        String centroAcopio;
        String destino;
        String tipoTransporte;

        if (objeto.contains("medicamento") || objeto.contains("remedio") || objeto.contains("vacuna")) {
            centroAcopio = "Farmacia Central / Hospital";
            destino = "Zona de Catástrofe - Centro Médico";
            tipoTransporte = "AEREO";
        } else if (objeto.contains("ropa") || objeto.contains("abrigo") || objeto.contains("calzado")) {
            centroAcopio = "Bodega Textil";
            destino = "Gimnasio Municipal (Albergue)";
            tipoTransporte = "TERRESTRE";
        } else {
            centroAcopio = "Bodega General";
            destino = "Punto de Acopio Estándar";
            tipoTransporte = "TERRESTRE";
        }

        // Instanciación nativa sin depender de .builder()
        EnvioRequestDTO request = new EnvioRequestDTO();
        request.setDonacionId(donacion.getId());
        request.setCentroAcopioOrigen(centroAcopio);
        request.setDestino(destino);
        request.setTipoTransporte(tipoTransporte);

        return request;
    }

    private EnvioResponseDTO mapToResponseDTO(Envio envio) {
        // Constructor nativo sin depender de .builder()
        return new EnvioResponseDTO(
                envio.getId(),
                envio.getCentroAcopioOrigen(),
                envio.getDonacionId(),
                envio.getDestino(),
                envio.getTipoTransporte(),
                envio.getEstado(),
                envio.getFechaCreacion()
        );
    }
}