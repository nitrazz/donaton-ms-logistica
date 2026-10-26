package com.gestionDonaton.gestion_logistica.factory;

import com.gestionDonaton.gestion_logistica.dto.DonacionDTO;
import com.gestionDonaton.gestion_logistica.dto.EnvioRequestDTO;
import com.gestionDonaton.gestion_logistica.entity.Envio;
import org.springframework.stereotype.Component;

@Component
public class EnvioFactory {

    public Envio crearEnvio(EnvioRequestDTO request) {
        Envio envio = new Envio();
        envio.setDonacionId(request.getDonacionId());
        envio.setCentroAcopioOrigen(request.getCentroAcopioOrigen());
        envio.setDestino(request.getDestino());
        envio.setTipoTransporte(validarTipoTransporte(request.getTipoTransporte()));
        envio.setEstado("EN_PREPARACION");
        return envio;
    }

    public Envio crearDesdeDonacion(DonacionDTO donacion) {
        Envio envio = new Envio();
        envio.setDonacionId(donacion.getId());
        envio.setCentroAcopioOrigen("CENTRO_CENTRAL_GENERICO");
        envio.setDestino("DESTINO_POR_DEFINIR");
        envio.setTipoTransporte("TERRESTRE");
        envio.setEstado("EN_PREPARACION");
        return envio;
    }

    private String validarTipoTransporte(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            return "TERRESTRE";
        }
        String tipoUpper = tipo.toUpperCase();
        if (tipoUpper.equals("AEREO") || tipoUpper.equals("MARITIMO") || tipoUpper.equals("TERRESTRE")) {
            return tipoUpper;
        }
        return "TERRESTRE";
    }
}