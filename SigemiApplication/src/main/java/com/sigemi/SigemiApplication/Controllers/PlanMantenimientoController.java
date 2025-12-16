
package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.PlanMantenimientoDTO;
import com.sigemi.SigemiApplication.Service.PlanMantenimientoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/planes")
public class PlanMantenimientoController {
    
    @Autowired
    private PlanMantenimientoService planService;
    
    @PostMapping
    public ResponseEntity<PlanMantenimientoDTO> crearPlan(@RequestBody PlanMantenimientoDTO dto) {
        PlanMantenimientoDTO nuevoPlan = planService.crearPlan(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPlan);
    }

    @GetMapping
    public ResponseEntity<List<PlanMantenimientoDTO>> listarPlanes() {
        return ResponseEntity.ok(planService.listarPlanes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanMantenimientoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(planService.obtenerPorId(id));
    }
    
    @GetMapping("/equipo/{idEquipo}")
    public ResponseEntity<List<PlanMantenimientoDTO>> listarPlanesPorEquipo(@PathVariable Long idEquipo) {
        return ResponseEntity.ok(planService.listarPlanesPorEquipo(idEquipo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanMantenimientoDTO> actualizarPlan(@PathVariable Long id, @RequestBody PlanMantenimientoDTO dto) {
        PlanMantenimientoDTO actualizado = planService.actualizarPlan(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivarPlan(@PathVariable Long id) {
        planService.desactivarPlan(id);
        return ResponseEntity.noContent().build();
    }
    
}
