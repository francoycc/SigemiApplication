
package com.sigemi.SigemiApplication.DTO;

import jakarta.validation.constraints.*;

public class TareaDTO {
    private Long id;
    
    @NotBlank
    private String descripcion;
    
    @NotBlank
    private String estado;
    
    @NotNull
    private Long tecnicoId;
    
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
    
    
}
