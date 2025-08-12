package com.sigemi.SigemiApplication.Entidades;

import com.sigemi.SigemiApplication.Enums.EstadoOperativo;
import com.sigemi.SigemiApplication.Enums.Criticidad;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Equipo")
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

    public Equipo() {
    }
    
    public Long getIdEquipo() {
        return idEquipo;
    }

    public String getCodigoEquipo() {
        return codigoEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public LocalDate getFechaIncorporacion() {
        return fechaIncorporacion;
    }

    public EstadoOperativo getEstadoOperativo() {
        return estadoOperativo;
    }

    public Criticidad getCriticidad() {
        return criticidad;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Boolean getActivo() {
        return activo;
    }

    public UbicacionTecnica getUbicacionTecnica() {
        return ubicacionTecnica;
    }

    public List<PlanMantenimiento> getPlanesMantenimiento() {
        return planesMantenimiento;
    }

    public void setIdEquipo(Long idEquipo) {
        this.idEquipo = idEquipo;
    }

    public void setCodigoEquipo(String codigoEquipo) {
        this.codigoEquipo = codigoEquipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public void setFechaIncorporacion(LocalDate fechaIncorporacion) {
        this.fechaIncorporacion = fechaIncorporacion;
    }

    public void setEstadoOperativo(EstadoOperativo estadoOperativo) {
        this.estadoOperativo = estadoOperativo;
    }

    public void setCriticidad(Criticidad criticidad) {
        this.criticidad = criticidad;
    }

    public void setFrecuencia(Integer frecuencia) {
        this.frecuencia = frecuencia;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public void setUbicacionTecnica(UbicacionTecnica ubicacionTecnica) {
        this.ubicacionTecnica = ubicacionTecnica;
    }

    public void setPlanesMantenimiento(List<PlanMantenimiento> planesMantenimiento) {
        this.planesMantenimiento = planesMantenimiento;
    }

    
    
}