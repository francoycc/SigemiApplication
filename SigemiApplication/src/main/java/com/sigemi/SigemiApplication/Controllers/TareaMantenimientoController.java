
package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import com.sigemi.SigemiApplication.Service.TareaMantenimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/tareas")
@RequiredArgsConstructor
public class TareaMantenimientoController {

    private final TareaMantenimientoService tareaService;

    @PostMapping
    public TareaMantenimiento crear(@RequestBody TareaMantenimiento tarea) {
        return tareaService.crearTarea(tarea);
    }

    @GetMapping
    public List<TareaMantenimiento> listarTodas() {
        return tareaService.listarTareas();
    }

    @GetMapping("/orden/{idOrden}")
    public List<TareaMantenimiento> listarPorOrden(@PathVariable Long idOrden) {
        return tareaService.listarPorOrden(idOrden);
    }

    @GetMapping("/{id}")
    public TareaMantenimiento obtenerPorId(@PathVariable Long id) {
        return tareaService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public TareaMantenimiento actualizar(@PathVariable Long id, @RequestBody TareaMantenimiento tarea) {
        return tareaService.actualizarTarea(id, tarea);
    }

    @PutMapping("/{id}/pausar")
    public void pausar(@PathVariable Long id) {
        tareaService.pausarTarea(id);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
}
