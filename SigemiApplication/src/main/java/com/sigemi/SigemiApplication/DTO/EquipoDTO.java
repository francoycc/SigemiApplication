package com.sigemi.SigemiApplication.DTO;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;


public class EquipoDTO {
    private Long idEquipo;
    @NotNull
    private String codigoEquipo;
    
    private String nombre;
    
    private String tipo;
    
    private String marca;
    
    private String modelo;
    @NotNull
    private String numeroSerie;
    @NotNull
    private LocalDate fechaIncorporacion;
    
    private String estadoOperativo; // Enum como String
    
    private String criticidad;      // Enum como String
    
    private Integer frecuencia;
    
    private String observaciones;
    
    private Boolean activo;
    
    private Long ubicacionTecnicaId; // solo el id para no anidar todo

    public EquipoDTO(){
        
    }
    public Long getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Long idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getCodigoEquipo() {
        return codigoEquipo;
    }

    public void setCodigoEquipo(String codigoEquipo) {
        this.codigoEquipo = codigoEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public LocalDate getFechaIncorporacion() {
        return fechaIncorporacion;
    }

    public void setFechaIncorporacion(LocalDate fechaIncorporacion) {
        this.fechaIncorporacion = fechaIncorporacion;
    }

    public String getEstadoOperativo() {
        return estadoOperativo;
    }

    public void setEstadoOperativo(String estadoOperativo) {
        this.estadoOperativo = estadoOperativo;
    }

    public String getCriticidad() {
        return criticidad;
    }

    public void setCriticidad(String criticidad) {
        this.criticidad = criticidad;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Long getUbicacionTecnicaId() {
        return ubicacionTecnicaId;
    }

    public void setUbicacionTecnicaId(Long ubicacionTecnicaId) {
        this.ubicacionTecnicaId = ubicacionTecnicaId;
    }
    
    
}
