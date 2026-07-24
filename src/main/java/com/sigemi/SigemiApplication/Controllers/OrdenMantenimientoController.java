
package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.OrdenDTO;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Entidades.Usuario;
import com.sigemi.SigemiApplication.Service.OrdenMantenimientoService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenMantenimientoController {
    
    @Autowired
    private final OrdenMantenimientoService ordenMantenimientoService;

    
    @PostMapping
    public ResponseEntity<OrdenDTO> crearOrden(@Valid @RequestBody OrdenDTO dto, 
            @RequestParam String usuarioCreador) {
        OrdenDTO nuevaOrden = ordenMantenimientoService.crearOrden(dto, usuarioCreador);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
    }
    
    @GetMapping
    public ResponseEntity<List<OrdenDTO>> listarOrdenes() {
        return ResponseEntity.ok(ordenMantenimientoService.listarOrdenes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ordenMantenimientoService.obtenerPorId(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<OrdenDTO> actualizarOrden(@PathVariable Long id,
                                                @Valid @RequestBody OrdenDTO ordenDTO){
        return ResponseEntity.ok(ordenMantenimientoService.actualizarOrden(id, ordenDTO));
    }
    
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<OrdenDTO> finalizarOrden(@PathVariable Long id){
        return ResponseEntity.ok(ordenMantenimientoService.finalizarOrden(id));
    }
    
    @GetMapping("/equipo/{idEquipo}")
    public ResponseEntity<Page<OrdenDTO>> listarPorEquipo(
            @PathVariable Long idEquipo,
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        
        return ResponseEntity.ok(ordenMantenimientoService.listarPorEquipo(idEquipo, pageable));
    }
}
