
package com.sigemi.SigemiApplication.Entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Repuesto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRepuesto;
    
    @Column(nullable = false, unique = true)
    private String codigo;
    
    @Column(nullable = false)
    private String descripcion;
    
    @Column(nullable = false)
    private String unidadMedida;
    
    @Column(nullable = false)
    private Integer stockActual;
    
    @Column(nullable = false)
    private Integer stockMinimo;


    // getters and setters
    public Long getidRepuesto() {
        return idRepuesto;
    }


    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
    
    
}
