
package Service;

import Entidades.EvidenciaTrabajo;
import java.util.List;


public interface EvidenciaTrabajoService {
    EvidenciaTrabajo crearEvidencia(EvidenciaTrabajo evidencia);
    List<EvidenciaTrabajo> listarPorTarea(Long idTarea);
}
