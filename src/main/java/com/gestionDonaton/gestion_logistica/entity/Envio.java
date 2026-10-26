package com.gestionDonaton.gestion_logistica.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "envios")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "donacion_id", nullable = false)
    private Long donacionId;

    @Column(name = "centro_acopio_origen", nullable = false)
    private String centroAcopioOrigen;

    @Column(nullable = false)
    private String destino;

    @Column(name = "tipo_transporte", nullable = false)
    private String tipoTransporte;

    @Column(nullable = false)
    private String estado;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = "EN_PREPARACION";
        }
    }

    // ── Métodos de acceso nativos para garantizar compatibilidad con la compilación ──
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getDonacionId() { return donacionId; }
    public void setDonacionId(Long donacionId) { this.donacionId = donacionId; }

    public String getCentroAcopioOrigen() { return centroAcopioOrigen; }
    public void setCentroAcopioOrigen(String centroAcopioOrigen) { this.centroAcopioOrigen = centroAcopioOrigen; }

    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }

    public String getTipoTransporte() { return tipoTransporte; }
    public void setTipoTransporte(String tipoTransporte) { this.tipoTransporte = tipoTransporte; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}