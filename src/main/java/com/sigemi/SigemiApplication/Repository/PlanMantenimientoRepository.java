
package com.sigemi.SigemiApplication.Repository;

import com.sigemi.SigemiApplication.Entidades.PlanMantenimiento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PlanMantenimientoRepository extends JpaRepository<PlanMantenimiento, Long> {
    
    // Busca todos los planes activos para que el scheduler los revise.
    List<PlanMantenimiento> findByActivoTrue();

    List<PlanMantenimiento> findByEquipo_IdEquipo(Long idEquipo);
}
