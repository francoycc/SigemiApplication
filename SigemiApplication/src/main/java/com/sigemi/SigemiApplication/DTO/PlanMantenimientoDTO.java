
package com.sigemi.SigemiApplication.DTO;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;



public class PlanMantenimientoDTO {
    private Long idPlan;

    @NotNull
    private String nombre;
    
    private String descripcion;

    @NotNull
    private Integer frecuenciaDias; 
    
    @NotNull
    private LocalDate fechaInicio; 

    private Boolean activo;

    @NotNull
    private Long equipoId; // id del equipo asociado
    
    private String equipoNombre; // opcional para mostrar

    // getters y setters
    public Long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Long idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getFrecuenciaDias() {
        return frecuenciaDias;
    }

    public void setFrecuenciaDias(Integer frecuenciaDias) {
        this.frecuenciaDias = frecuenciaDias;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(Long equipoId) {
        this.equipoId = equipoId;
    }

    public String getEquipoNombre() {
        return equipoNombre;
    }

    public void setEquipoNombre(String equipoNombre) {
        this.equipoNombre = equipoNombre;
    }
    
}
