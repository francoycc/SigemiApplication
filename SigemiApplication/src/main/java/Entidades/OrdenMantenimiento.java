
package Entidades;

import Enums.EstadoOrden;
import Enums.TipoMantenimiento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "ordenMantenimiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdenMantenimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOrden;
    
    @Column(nullable = false, unique = true)
    private String codigoOrden;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMantenimiento tipo;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoOrden estado;

    // Asociacion con el equipo
    @ManyToOne
    @JoinColumn(name = "id_equipo", nullable = false)
    private Equipo equipo;

    // Asociacion con el supervisor que creo la orden
    @ManyToOne
    @JoinColumn(name = "id_supervisor")
    private Usuario supervisor;

    // Tecnicos asignados a la orden
    @ManyToMany
    @JoinTable(
        name = "orden_tecnico",
        joinColumns = @JoinColumn(name = "id_orden"),
        inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private List<Usuario> tecnicosAsignados;

    // Tareas de mantenimiento registradas
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TareaMantenimiento> tareas;

    // Repuestos usados
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsoRepuesto> repuestosUtilizados;

    // Metodo para saber si puede finalizarse
    /*
    public boolean puedeFinalizarse() {
        return tareas != null && tareas.stream().allMatch(t -> t.getEstado().equals(EstadoTarea.COMPLETADA));
    }*/
    
}
