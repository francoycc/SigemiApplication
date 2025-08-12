package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.Entidades.EvidenciaTrabajo;
import com.sigemi.SigemiApplication.Repository.EvidenciaTrabajoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EvidenciaTrabajoServiceImpl implements EvidenciaTrabajoService {
    
    @Autowired
    private final EvidenciaTrabajoRepository evidenciaRepository;
    
    public EvidenciaTrabajoServiceImpl(EvidenciaTrabajoRepository evidenciaRepo){
        this.evidenciaRepository = evidenciaRepo;
    }
    
    @Override
    public EvidenciaTrabajo crearEvidencia(EvidenciaTrabajo evidencia) {
        return evidenciaRepository.save(evidencia);
    }

    @Override
    public List<EvidenciaTrabajo> listarPorTarea(Long idTarea) {
        return evidenciaRepository.findByTarea_IdTarea(idTarea);
    }
    
}
