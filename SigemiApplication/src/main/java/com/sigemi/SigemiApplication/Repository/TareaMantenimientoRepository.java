
package com.sigemi.SigemiApplication.Repository;

import java.util.List;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TareaMantenimientoRepository extends JpaRepository<TareaMantenimiento, Long> {
    @EntityGraph(attributePaths = {"equipo"})
    List<TareaMantenimiento> findAll();
    
    // Para cuando necesites ver el historial de un equipo específico
    List<TareaMantenimiento> findByEquipo_IdEquipo(Long idEquipo);
    List<TareaMantenimiento> findByOrden_IdOrden(Long idOrden);
    
    //Busca todas las tareas asignadas a un tecnico especifico.
    List<TareaMantenimiento> findByTecnico_IdUsuario(Long idTecnico);
}
