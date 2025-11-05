

package com.sigemi.SigemiApplication.DTO;

import java.time.LocalDate;
import java.util.List;

/*
 DTO para CU28: Representa el historial completo de una orden de mantenimiento.
 */
public class HistorialOrdenDTO {

    // datos de la Orden solo lectura
    private Long idOrden;
    private String codigoOrden;
    private String tipo; // Preventivo, Correctivo, Predictivo
    private String descripcion;
    private String prioridad;
    private LocalDate fechaCreacion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado; // Abierta, Finalizada, etc.
    
    // datos del Equipo  
    private Long equipoId;
    private String equipoNombre;

    // datos Agregados para el Historial 
    private List<TareaDTO> tareas;
    private List<UsoRepuestoDTO> repuestosUtilizados;

    // Getters y Setters
    public Long getIdOrden() {
        return idOrden;
    }
    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
    }
    public String getCodigoOrden() {
        return codigoOrden;
    }
    public void setCodigoOrden(String codigoOrden) {
        this.codigoOrden = codigoOrden;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
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
    public List<TareaDTO> getTareas() {
        return tareas;
    }
    public void setTareas(List<TareaDTO> tareas) {
        this.tareas = tareas;
    }
    public List<UsoRepuestoDTO> getRepuestosUtilizados() {
        return repuestosUtilizados;
    }
    public void setRepuestosUtilizados(List<UsoRepuestoDTO> repuestosUtilizados) {
        this.repuestosUtilizados = repuestosUtilizados;
    }
}