
package com.sigemi.SigemiApplication.DTO;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class TareaDTO {
    private Long id;
    
    private String tipo; // mapeado desde el enum TipoMantenimiento
    private String descripcion;
    private LocalDate fechaEjecucion;
    
    @NotBlank
    private String estado;
    
    private Double tiempoInvertidoHoras;
    @NotNull
    private Long tecnicoId;
    // Relación con la orden
    private Long ordenId;
    @NotBlank
    private String tecnicoNombre; 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getTecnicoId() {
        return tecnicoId;
    }

    public void setTecnicoId(Long tecnicoId) {
        this.tecnicoId = tecnicoId;
    }

    public String getTecnicoNombre() {
        return tecnicoNombre;
    }

    public void setTecnicoNombre(String tecnicoNombre) {
        this.tecnicoNombre = tecnicoNombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(LocalDate fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public Double getTiempoInvertidoHoras() {
        return tiempoInvertidoHoras;
    }

    public void setTiempoInvertidoHoras(Double tiempoInvertidoHoras) {
        this.tiempoInvertidoHoras = tiempoInvertidoHoras;
    }

    public Long getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Long ordenId) {
        this.ordenId = ordenId;
    }
}
