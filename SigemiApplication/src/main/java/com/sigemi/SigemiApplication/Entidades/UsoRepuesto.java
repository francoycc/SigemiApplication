
package com.sigemi.SigemiApplication.Entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "UsoRepuesto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsoRepuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsoRepuesto;
    
    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenMantenimiento orden;
    
    @ManyToOne
    @JoinColumn(name = "id_repuesto", nullable = false)
    private Repuesto repuesto;
    
    @Column(nullable = false)
    private Integer cantidadUtilizada;

    
    // getters and setters
    public Long getIdUsoRepuesto() {
        return idUsoRepuesto;
    }

    public OrdenMantenimiento getOrden() {
        return orden;
    }

    public void setOrden(OrdenMantenimiento orden) {
        this.orden = orden;
    }

    public Repuesto getRepuesto() {
        return repuesto;
    }

    public void setRepuesto(Repuesto repuesto) {
        this.repuesto = repuesto;
    }

    public Integer getCantidadUtilizada() {
        return cantidadUtilizada;
    }

    public void setCantidadUtilizada(Integer cantidadUtilizada) {
        this.cantidadUtilizada = cantidadUtilizada;
    }
}
