package com.gestionDonaton.gestion_logistica.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EnvioRequestDTO {
    private Long donacionId;
    private String centroAcopioOrigen;
    private String destino;
    private String tipoTransporte;

    public EnvioRequestDTO() {}

    public EnvioRequestDTO(Long donacionId, String centroAcopioOrigen, String destino, String tipoTransporte) {
        this.donacionId = donacionId;
        this.centroAcopioOrigen = centroAcopioOrigen;
        this.destino = destino;
        this.tipoTransporte = tipoTransporte;
    }

    public Long getDonacionId() { return donacionId; }
    public void setDonacionId(Long donacionId) { this.donacionId = donacionId; }

    public String getCentroAcopioOrigen() { return centroAcopioOrigen; }
    public void setCentroAcopioOrigen(String centroAcopioOrigen) { this.centroAcopioOrigen = centroAcopioOrigen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getTipoTransporte() { return tipoTransporte; }
    public void setTipoTransporte(String tipoTransporte) { this.tipoTransporte = tipoTransporte; }
}