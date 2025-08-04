package Repository;

import Entidades.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    
}
