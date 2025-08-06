package Repository;

import Entidades.EvidenciaTrabajo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EvidenciaTrabajoRepository extends JpaRepository<EvidenciaTrabajo, Long> {
     List<EvidenciaTrabajo> findByTarea(Long idTarea);
}
