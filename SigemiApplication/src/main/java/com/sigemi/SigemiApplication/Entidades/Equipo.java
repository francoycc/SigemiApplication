package com.sigemi.SigemiApplication.Entidades;

import com.sigemi.SigemiApplication.Enums.EstadoOperativo;
import com.sigemi.SigemiApplication.Enums.Criticidad;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Equipo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipo;

    @Column(nullable = false, unique = true)
    private String codigoEquipo;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String tipo;
    private String marca;
    private String modelo;
    @Column(unique = true)
    private String numeroSerie;

    private LocalDate fechaIncorporacion;

    @Enumerated(EnumType.STRING)
    private EstadoOperativo estadoOperativo;

    @Enumerated(EnumType.STRING)
    private Criticidad criticidad;

    private Integer frecuencia;
    @Column(length = 1000)
    private String observaciones;

    private Boolean activo;

    // Relacion con Ubicacion Tecnica
    @ManyToOne
    @JoinColumn(name = "id_ubicacion")
    private UbicacionTecnica ubicacionTecnica;
    
    // Relacion con Planes de Mantenimiento
    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanMantenimiento> planesMantenimiento;

}