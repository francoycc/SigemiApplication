
package com.sigemi.SigemiApplication.Repository;

import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Enums.EstadoOrden;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface OrdenMantenimientoRepository extends JpaRepository<OrdenMantenimiento, Long> {
    @Query("SELECT o FROM OrdenMantenimiento o WHERE o.fechaInicio >= :inicio AND o.fechaInicio <= :fin AND o.estado = :estado")
    List<OrdenMantenimiento> buscarPorFechaYEstado(LocalDate inicio, LocalDate fin, EstadoOrden estado);
    // List<OrdenMantenimiento> findByEquipo_IdEquipo(Long idEquipo);
    
    @EntityGraph(attributePaths = {"equipo", "supervisor", "tareas", "repuestosUtilizados"})
    List<OrdenMantenimiento> findByEquipo_IdEquipo(Long idEquipo);
    
    @Query("SELECT o FROM OrdenMantenimiento o " +
       "LEFT JOIN FETCH o.tareas " +
       "LEFT JOIN FETCH o.repuestosUtilizados " +
       "WHERE o.equipo.idEquipo = :idEquipo")
    List<OrdenMantenimiento> buscarOrdenesCompletasPorEquipo(Long idEquipo);
}
