
package com.sigemi.SigemiApplication.DTO;

import java.time.LocalDate;


public class FallaReportadaDTO {
    private Long idFalla;
    private String nombreEquipo;
    private Long equipoId;
    private Long reportadoPorId;
    private String nombreUsuarioReporta;
    private String descripcion;
    private String criticidad;
    private LocalDate fechaReporte;
    private String codigoOrdenGenerada;
    private Long tecnicoAsignadoId;

    public Long getTecnicoAsignadoId() {
        return tecnicoAsignadoId;
    }

    public void setTecnicoAsignadoId(Long tecnicoAsignadoId) {
        this.tecnicoAsignadoId = tecnicoAsignadoId;
    }

   

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
     public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public void setNombreUsuarioReporta(String nombreUsuarioReporta) {
        this.nombreUsuarioReporta = nombreUsuarioReporta;
    }

    public void setCodigoOrdenGenerada(String codigoOrdenGenerada) {
        this.codigoOrdenGenerada = codigoOrdenGenerada;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public String getNombreUsuarioReporta() {
        return nombreUsuarioReporta;
    }

    public String getCodigoOrdenGenerada() {
        return codigoOrdenGenerada;
    }
}
