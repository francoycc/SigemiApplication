
package com.sigemi.SigemiApplication.Repository;

import java.util.List;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TareaMantenimientoRepository extends JpaRepository<TareaMantenimiento, Long> {
    List<TareaMantenimiento> findByOrden_IdOrden(Long idOrden);
    
    //Busca todas las tareas asignadas a un tecnico especifico.
    List<TareaMantenimiento> findByTecnico_IdUsuario(Long idTecnico);
}
