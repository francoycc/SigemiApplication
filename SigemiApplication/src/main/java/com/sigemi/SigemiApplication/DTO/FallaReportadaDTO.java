
package com.sigemi.SigemiApplication.DTO;

import java.time.LocalDate;


public class FallaReportadaDTO {
    private Long idFalla;
    private Long equipoId;
    private Long reportadoPorId;
    private String descripcion;
    private String criticidad;
    private LocalDate fechaReporte;

    public Long getIdFalla() {
        return idFalla;
    }

    public void setIdFalla(Long idFalla) {
        this.idFalla = idFalla;
    }

    public Long getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(Long equipoId) {
        this.equipoId = equipoId;
    }

    public Long getReportadoPorId() {
        return reportadoPorId;
    }

    public void setReportadoPorId(Long reportadoPorId) {
        this.reportadoPorId = reportadoPorId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCriticidad() {
        return criticidad;
    }

    public void setCriticidad(String nivelSeveridad) {
        this.criticidad = nivelSeveridad;
    }

    public LocalDate getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDate fechaReporte) {
        this.fechaReporte = fechaReporte;
    }
    
}
