package com.gestionDonaton.gestion_logistica.dto;




public class DonacionDTO {
    private Long id;
    private String nombreObjeto;
    private Double monto;

    public DonacionDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreObjeto() { return nombreObjeto; }
    public void setNombreObjeto(String nombreObjeto) { this.nombreObjeto = nombreObjeto; }

    public Double getMonto() { return monto; }
    public void setMonto(Double monto) { this.monto = monto; }
}