
package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.Entidades.EvidenciaTrabajo;
import java.util.List;


public interface EvidenciaTrabajoService {
    EvidenciaTrabajo crearEvidencia(EvidenciaTrabajo evidencia);
    List<EvidenciaTrabajo> listarPorTarea(Long idTarea);
}
