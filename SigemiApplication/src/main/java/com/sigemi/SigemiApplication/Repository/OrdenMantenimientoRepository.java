
package com.sigemi.SigemiApplication.Repository;

import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrdenMantenimientoRepository extends JpaRepository<OrdenMantenimiento, Long> {
    List<OrdenMantenimiento> findByEquipo_IdEquipo(Long idEquipo);
}
