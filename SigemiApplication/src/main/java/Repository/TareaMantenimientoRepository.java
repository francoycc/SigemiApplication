/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Repository.java to edit this template
 */
package Repository;

import java.util.List;
import Entidades.TareaMantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TareaMantenimientoRepository extends JpaRepository<TareaMantenimiento, Long> {
    List<TareaMantenimiento> findByOrden(Long idOrden);
}
