
package com.sigemi.SigemiApplication.Entidades;

import com.sigemi.SigemiApplication.Enums.EstadoOrden;
import com.sigemi.SigemiApplication.Enums.TipoMantenimiento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "OrdenMantenimiento")
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
    
    @Column(length = 50)
    private String prioridad;

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
        name = "ordenTecnico",
        joinColumns = @JoinColumn(name = "id_orden"),
        inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private List<Usuario> tecnicosAsignados;

    // Tareas de mantenimiento registradas
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TareaMantenimiento> tareas;

    // Repuestos usados
    //@OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    //private List<UsoRepuesto> repuestosUtilizados;

    // Metodo para saber si puede finalizarse
    /*
    public boolean puedeFinalizarse() {
        return tareas != null && tareas.stream().allMatch(t -> t.getEstado().equals(EstadoTarea.COMPLETADA));
    }*/

    public Long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
    }

    public String getCodigoOrden() {
        return codigoOrden;
    }

    public void setCodigoOrden(String codigoOrden) {
        this.codigoOrden = codigoOrden;
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

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Usuario getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Usuario supervisor) {
        this.supervisor = supervisor;
    }

    public List<Usuario> getTecnicosAsignados() {
        return tecnicosAsignados;
    }

    public void setTecnicosAsignados(List<Usuario> tecnicosAsignados) {
        this.tecnicosAsignados = tecnicosAsignados;
    }

    public List<TareaMantenimiento> getTareas() {
        return tareas;
    }

    public void setTareas(List<TareaMantenimiento> tareas) {
        this.tareas = tareas;
    }

    public void addTarea(TareaMantenimiento tarea) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
