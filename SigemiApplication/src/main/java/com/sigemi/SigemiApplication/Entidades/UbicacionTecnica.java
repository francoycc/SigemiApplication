
package com.sigemi.SigemiApplication.Entidades;

import com.sigemi.SigemiApplication.Enums.EstadoOperativo;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@Table(name = "UbicacionTecnica")
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

    public Long getIdUbicacion() {
        return idUbicacion;
    }

    public void setIdUbicacion(Long idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public EstadoOperativo getEstado() {
        return estado;
    }

    public void setEstado(EstadoOperativo estado) {
        this.estado = estado;
    }

    public UbicacionTecnica getUbicacionPadre() {
        return ubicacionPadre;
    }

    public void setUbicacionPadre(UbicacionTecnica ubicacionPadre) {
        this.ubicacionPadre = ubicacionPadre;
    }

    public List<UbicacionTecnica> getSubUbicaciones() {
        return subUbicaciones;
    }

    public void setSubUbicaciones(List<UbicacionTecnica> subUbicaciones) {
        this.subUbicaciones = subUbicaciones;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    
    
    public boolean estaActiva() {
        return estado == EstadoOperativo.Operativo;
    }
    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}
