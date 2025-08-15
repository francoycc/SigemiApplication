package com.sigemi.SigemiApplication.DTO;

import jakarta.validation.constraints.NotNull;
import java.util.List;


public class UbicacionTecnicaDTO {
    private Long idUbicacion;
    @NotNull
    private String codigo;
    @NotNull
    private String nombre;
    
    private String tipo;
    
    private String estado;
    
    private Long idPadre;
    
    private List<UbicacionTecnicaDTO> sububicaciones;
    
    public UbicacionTecnicaDTO(){
        
    }

    public Long getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    public List<UbicacionTecnicaDTO> getSububicaciones() {
        return sububicaciones;
    }

    public void setSububicaciones(List<UbicacionTecnicaDTO> sububicaciones) {
        this.sububicaciones = sububicaciones;
    }
    
}
