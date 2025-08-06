
package Controllers;

import Entidades.EvidenciaTrabajo;
import Service.EvidenciaTrabajoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/evidencias")
@RequiredArgsConstructor
public class EvidenciaTrabajoController {

    private final EvidenciaTrabajoService evidenciaService;

    @PostMapping
    public EvidenciaTrabajo crear(@RequestBody EvidenciaTrabajo evidencia) {
        return evidenciaService.crearEvidencia(evidencia);
    }

    @GetMapping("/tarea/{idTarea}")
    public List<EvidenciaTrabajo> listarPorTarea(@PathVariable Long idTarea) {
        return evidenciaService.listarPorTarea(idTarea);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
}
