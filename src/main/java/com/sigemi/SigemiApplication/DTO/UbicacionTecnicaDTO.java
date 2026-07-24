package com.sigemi.SigemiApplication.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;


public class UbicacionTecnicaDTO {
    private Long idUbicacion;
    @NotBlank(message = "El código técnico es obligatorio")
    @Size(max = 50, message = "El código no puede exceder los 50 caracteres")
    private String codigo;
    @NotBlank(message = "El nombre de la ubicacion es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;
    @NotBlank(message = "El tipo de ubicación es obligatorio")
    private String tipo;
    @NotBlank(message = "El estado operativo de la ubicación es obligatorio")
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
