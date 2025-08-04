package Entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "evidenciaTrabajo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvidenciaTrabajo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvidencia;

    @Column(nullable = false)
    private String tipoArchivo; // Ej: "imagen", "pdf", "texto"

    @Column(nullable = false)
    private String nombreArchivo; // Nombre original del archivo

    @Column(length = 1000)
    private String descripcion; // Descripción breve de lo evidenciado

    // Asociacion con la tarea que genero esta evidencia
    @ManyToOne
    @JoinColumn(name = "id_tarea", nullable = false)
    private TareaMantenimiento tarea;
    
    @Column(nullable = false)
    private String urlArchivo;
}
