package Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipo;

    private String codigoEquipo;
    private String nombre;
    private String tipo;
    private String marca;
    private String modelo;
    private String numeroSerie;

    @Temporal(TemporalType.DATE)
    private Date fechaIncorporacion;

    @Enumerated(EnumType.STRING)
    private EstadoOperativo estadoOperativo;

    @Enumerated(EnumType.STRING)
    private Criticidad criticidad;

    private String frecuencia;
    private String observaciones;

    private Boolean activo;

    // Relación con Ubicación Técnica (muchos a uno)
    @ManyToOne
    @JoinColumn(name = "idUbicacion")
    private UbicacionTecnica ubicacionTecnica;
}