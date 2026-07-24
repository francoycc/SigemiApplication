
package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.TareaDTO;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import com.sigemi.SigemiApplication.Service.TareaMantenimientoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/tareas")
public class TareaMantenimientoController {

    @Autowired
    private final TareaMantenimientoService tareaService;

    public TareaMantenimientoController(TareaMantenimientoService tservice){
        this.tareaService = tservice;
    };
    
    @PostMapping
    public ResponseEntity<TareaDTO> crearTarea(@Valid @RequestBody TareaDTO dto) {
        TareaDTO nuevaTarea = tareaService.crearTarea(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTarea);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<TareaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tareaService.obtenerPorId(id));
    }

    
    @GetMapping
    public ResponseEntity<List<TareaDTO>> listarTareas() {
        return ResponseEntity.ok(tareaService.listarTareas());
    }

    
    @GetMapping("/orden/{idOrden}")
    public ResponseEntity<List<TareaDTO>> listarPorOrden(@PathVariable Long idOrden) {
        return ResponseEntity.ok(tareaService.listarPorOrden(idOrden));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<TareaDTO> actualizarTarea(@Valid @PathVariable Long id,
            @RequestBody TareaDTO dto) {
        return ResponseEntity.ok(tareaService.actualizarTarea(id, dto));
    }

    
    @PatchMapping("/{id}/pausar")
    public ResponseEntity<Void> pausarTarea(@PathVariable Long id) {
        tareaService.pausarTarea(id);
        return ResponseEntity.noContent().build();
    }
    
    // permite al técnico consultar su lista de tareas
    @GetMapping("/tecnico/{idTecnico}")
    public ResponseEntity<List<TareaDTO>> listarPorTecnico(@PathVariable Long idTecnico) {
        return ResponseEntity.ok(tareaService.listarPorTecnico(idTecnico));
    }
}
