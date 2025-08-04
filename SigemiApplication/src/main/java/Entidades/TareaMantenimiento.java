
package Entidades;

import Enums.EstadoTarea;
import Enums.TipoMantenimiento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tareaMantenimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class TareaMantenimiento {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTarea;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMantenimiento tipo;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fechaEjecucion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTarea estado;

    private Double tiempoInvertidoHoras;

    // Relación con el técnico responsable
    @ManyToOne
    @JoinColumn(name = "id_tecnico", nullable = false)
    private Usuario tecnico;

    // Relación con la orden de mantenimiento
    @ManyToOne
    @JoinColumn(name = "id_orden", nullable = false)
    private OrdenMantenimiento orden;

    // Evidencias cargadas (texto, imagen, PDF, etc.)
    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvidenciaTrabajo> evidencias;
}
