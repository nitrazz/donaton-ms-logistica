package com.gestionDonaton.gestion_logistica.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import java.time.LocalDateTime;

public class EnvioResponseDTO {
    private Long id;
    private String centroAcopioOrigen;
    private Long donacionId;
    private String destino;
    private String tipoTransporte;
    private String estado;
    private LocalDateTime fechaCreacion;

    public EnvioResponseDTO() {}

    public EnvioResponseDTO(Long id, String centroAcopioOrigen, Long donacionId, String destino, String tipoTransporte, String estado, LocalDateTime fechaCreacion) {
        this.id = id;
        this.centroAcopioOrigen = centroAcopioOrigen;
        this.donacionId = donacionId;
        this.destino = destino;
        this.tipoTransporte = tipoTransporte;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() { return id; }
    public String getCentroAcopioOrigen() { return centroAcopioOrigen; }
    public Long getDonacionId() { return donacionId; }
    public String getDestino() { return destino; }
    public String getTipoTransporte() { return tipoTransporte; }
    public String getEstado() { return estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
}