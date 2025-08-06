package Service;

import Entidades.EvidenciaTrabajo;
import Repository.EvidenciaTrabajoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvidenciaTrabajoServiceImpl implements EvidenciaTrabajoService {
    
    private final EvidenciaTrabajoRepository EvidenciaRepository;
    
    @Override
    public EvidenciaTrabajo crearEvidencia(EvidenciaTrabajo evidencia) {
        return EvidenciaRepository.save(evidencia);
    }

    @Override
    public List<EvidenciaTrabajo> listarPorTarea(Long idTarea) {
        return EvidenciaRepository.findByTarea(idTarea);
    }
    
}
