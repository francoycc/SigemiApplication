package com.sigemi.SigemiApplication.Entidades;


import com.sigemi.SigemiApplication.Enums.Criticidad;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Table(name = "FallaReportada")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FallaReportada {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFalla;
    
    @ManyToOne
    @JoinColumn(name = "id_equipo", nullable = false)
    private Equipo equipo;
    
    @ManyToOne
    @JoinColumn(name = "id_reportado_por", nullable = false)
    private Usuario reportadoPor;
    
    @Column(nullable = false, length = 1000)
    private String descripcion;
    
    @Column(nullable = false)
    private LocalDate fechaReporte;
    
    private Criticidad criticidad;

    public Long getIdFalla() {
        return idFalla;
    }

    public void setIdFalla(Long idFalla) {
        this.idFalla = idFalla;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Usuario getReportadoPor() {
        return reportadoPor;
    }

    public void setReportadoPor(Usuario reportadoPor) {
        this.reportadoPor = reportadoPor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDate fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public Criticidad getCriticidad() {
        return criticidad;
    }

    public void setCriticidad(Criticidad criticidad) {
        this.criticidad = criticidad;
    }

}
