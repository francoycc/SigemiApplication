
package com.sigemi.SigemiApplication.Entidades;

import com.sigemi.SigemiApplication.Enums.EstadoOperativo;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@Table(name = "UbicacionTecnica")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UbicacionTecnica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUbicacion;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoOperativo estado;

    // Relación recursiva (una ubicación puede tener sub-ubicaciones)
    @ManyToOne
    @JoinColumn(name = "id_ubicacion_padre")
    private UbicacionTecnica ubicacionPadre;

    @OneToMany(mappedBy = "ubicacionPadre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UbicacionTecnica> subUbicaciones;

    // Relación con equipos
    @OneToMany(mappedBy = "ubicacionTecnica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Equipo> equipos;

    public boolean estaActiva() {
        return estado == EstadoOperativo.Operativo;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}
