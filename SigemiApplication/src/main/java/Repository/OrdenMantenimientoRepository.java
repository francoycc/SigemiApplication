
package Repository;

import Entidades.OrdenMantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrdenMantenimientoRepository extends JpaRepository<OrdenMantenimiento, Long> {
    
}
