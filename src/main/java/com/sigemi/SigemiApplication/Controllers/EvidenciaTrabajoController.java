
package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.Entidades.EvidenciaTrabajo;
import com.sigemi.SigemiApplication.Service.EvidenciaTrabajoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/evidencias")
public class EvidenciaTrabajoController {

    @Autowired
    private final EvidenciaTrabajoService evidenciaService;

    public EvidenciaTrabajoController(EvidenciaTrabajoService etService){
        this.evidenciaService = etService;
    }
    @PostMapping
    public EvidenciaTrabajo crear(@Valid @RequestBody EvidenciaTrabajo evidencia) {
        return evidenciaService.crearEvidencia(evidencia);
    }

    @GetMapping("/tarea/{idTarea}")
    public List<EvidenciaTrabajo> listarPorTarea(@PathVariable Long idTarea) {
        return evidenciaService.listarPorTarea(idTarea);
    }
    
    
}
