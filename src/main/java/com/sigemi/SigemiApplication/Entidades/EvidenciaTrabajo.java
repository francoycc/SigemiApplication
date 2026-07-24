package com.sigemi.SigemiApplication.Entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "EvidenciaTrabajo")
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

    public Long getIdEvidencia() {
        return idEvidencia;
    }

    public void setIdEvidencia(Long idEvidencia) {
        this.idEvidencia = idEvidencia;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlArchivo() {
        return urlArchivo;
    }

    public void setUrlArchivo(String urlArchivo) {
        this.urlArchivo = urlArchivo;
    }

    public TareaMantenimiento getTarea() {
        return tarea;
    }

    public void setTarea(TareaMantenimiento tarea) {
        this.tarea = tarea;
    }
}
