
package com.sigemi.SigemiApplication.Repository;

import java.util.List;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TareaMantenimientoRepository extends JpaRepository<TareaMantenimiento, Long> {
    @EntityGraph(attributePaths = {"orden", "tecnico"})
    List<TareaMantenimiento> findAll();
    
//    Para cuando necesites ver el historial de un equipo específico
//    List<TareaMantenimiento> findByEquipo_IdEquipo(Long idEquipo);
    @EntityGraph(attributePaths = {"orden", "tecnico"})
    List<TareaMantenimiento> findByOrden_IdOrden(Long idOrden);
    
    //Busca todas las tareas asignadas a un tecnico especifico.
    @EntityGraph(attributePaths = {"orden", "tecnico"})
    List<TareaMantenimiento> findByTecnico_IdUsuario(Long idTecnico);
}
