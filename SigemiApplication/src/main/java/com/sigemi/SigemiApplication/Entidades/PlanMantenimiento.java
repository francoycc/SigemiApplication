package com.sigemi.SigemiApplication.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "PlanMantenimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanMantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlan;
    
    @Column(nullable = false)
    private String nombre;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private Integer frecuenciaDias;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    private Boolean activo;

    // Asociacion con el equipo al que aplica este plan
    @ManyToOne
    @JoinColumn(name = "id_equipo", nullable = false)
    private Equipo equipo;
    
    public LocalDate getProximaFechaEjecucion() {
        return fechaInicio.plusDays(frecuenciaDias);
    }
    
}
