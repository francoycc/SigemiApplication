package com.sigemi.SigemiApplication.DTO;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.*;

public class OrdenDTO {
    private Long id;
    @NotNull
    private String tipo;

    @NotNull
    private Long equipoId;

    @NotNull
    private Long supervisorId;

    @NotNull
    private LocalDate fechaCreacion;
    
    @NotNull
    private LocalDate fechaPrevistaEjecucion;

    @NotEmpty
    private List<TareaDTO> tareas;
    
    private List<String> nombresTecnicos;
    
    @NotNull
    private String prioridad;
    
    private String estadoOrden;
    
    private String descripcion;

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(Long equipoId) {
        this.equipoId = equipoId;
    }

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaPrevistaEjecucion() {
        return fechaPrevistaEjecucion;
    }

    public void setFechaPrevistaEjecucion(LocalDate fechaPrevistaEjecucion) {
        this.fechaPrevistaEjecucion = fechaPrevistaEjecucion;
    }
    
    public List<String> getNombresTecnicos() {
        return nombresTecnicos;
    }

    public void setNombresTecnicos(List<String> nombresTecnicos) {
        this.nombresTecnicos = nombresTecnicos;
    }

    public List<TareaDTO> getTareas() {
        return tareas;
    }

    public void setTareas(List<TareaDTO> tareas) {
        this.tareas = tareas;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(String estadoOrden) {
        this.estadoOrden = estadoOrden;
    }
    
    
}
