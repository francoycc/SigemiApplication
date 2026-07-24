
package com.sigemi.SigemiApplication.DTO;

import jakarta.validation.constraints.NotNull;


public class UsoRepuestoDTO {
    private Long idUsoRepuesto;
    
    @NotNull 
    private Long ordenId;
    
    @NotNull
    private Long repuestoId;
    
    @NotNull
    private Integer cantidadUtilizada;
    
    // para mostrar info en el DTO de respuesta
    private String repuestoCodigo;
    private String repuestoDescripcion;

    public Long getIdUsoRepuesto() {
        return idUsoRepuesto;
    }

    public void setIdUsoRepuesto(Long idUsoRepuesto) {
        this.idUsoRepuesto = idUsoRepuesto;
    }

    public Long getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Long ordenId) {
        this.ordenId = ordenId;
    }

    public Long getRepuestoId() {
        return repuestoId;
    }

    public void setRepuestoId(Long repuestoId) {
        this.repuestoId = repuestoId;
    }

    public Integer getCantidadUtilizada() {
        return cantidadUtilizada;
    }

    public void setCantidadUtilizada(Integer cantidadUtilizada) {
        this.cantidadUtilizada = cantidadUtilizada;
    }

    public String getRepuestoCodigo() {
        return repuestoCodigo;
    }

    public void setRepuestoCodigo(String repuestoCodigo) {
        this.repuestoCodigo = repuestoCodigo;
    }

    public String getRepuestoDescripcion() {
        return repuestoDescripcion;
    }

    public void setRepuestoDescripcion(String repuestoDescripcion) {
        this.repuestoDescripcion = repuestoDescripcion;
    }
    
    
}
