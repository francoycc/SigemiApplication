
package com.sigemi.SigemiApplication.Entidades;

import com.sigemi.SigemiApplication.Enums.EstadoTarea;
import com.sigemi.SigemiApplication.Enums.TipoMantenimiento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TareaMantenimiento")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TareaMantenimiento {
    
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

    // Relacion con el tecnico responsable
    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario tecnico;

    // Relación con la orden de mantenimiento
    @ManyToOne
    @JoinColumn(name = "idOrden", nullable = false)
    private OrdenMantenimiento orden;

    // Evidencias cargadas (texto, imagen, PDF, etc.)
    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvidenciaTrabajo> evidencias;

    public Long getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Long idTarea) {
        this.idTarea = idTarea;
    }

    public TipoMantenimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMantenimiento tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaEjecucion() {
        return fechaEjecucion;
    }

    public void setFechaEjecucion(LocalDate fechaEjecucion) {
        this.fechaEjecucion = fechaEjecucion;
    }

    public EstadoTarea getEstado() {
        return estado;
    }

    public void setEstado(EstadoTarea estado) {
        this.estado = estado;
    }

    public Double getTiempoInvertidoHoras() {
        return tiempoInvertidoHoras;
    }

    public void setTiempoInvertidoHoras(Double tiempoInvertidoHoras) {
        this.tiempoInvertidoHoras = tiempoInvertidoHoras;
    }

    public Usuario getTecnico() {
        return tecnico;
    }

    public void setTecnico(Usuario tecnico) {
        this.tecnico = tecnico;
    }

    public OrdenMantenimiento getOrden() {
        return orden;
    }

    public void setOrden(OrdenMantenimiento orden) {
        this.orden = orden;
    }

    public List<EvidenciaTrabajo> getEvidencias() {
        return evidencias;
    }

    public void setEvidencias(List<EvidenciaTrabajo> evidencias) {
        this.evidencias = evidencias;
    }
    
    
}
