package com.sigemi.SigemiApplication.Entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EvidenciaTrabajo")
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
    private String tipoArchivo; 

    @Column(nullable = false)
    private String nombreArchivo;

    @Column(length = 1000)
    private String descripcion; 
    
    @Column(length = 50)
    private String urlArchivo;
    
    // Asociacion con la tarea que genero esta evidencia
    @ManyToOne
    @JoinColumn(name = "id_tarea", nullable = false)
    private TareaMantenimiento tarea;
}
