
package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.OrdenDTO;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Entidades.Usuario;
import com.sigemi.SigemiApplication.Service.OrdenMantenimientoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenMantenimientoController {
    
    @Autowired
    private final OrdenMantenimientoService ordenMantenimientoService;
    
    
//    
//    @PostMapping
//    public OrdenMantenimiento crear(@RequestBody OrdenMantenimiento orden){
//        return ordenMantenimientoService.crearOrdenMantenimiento(orden);
//    }
//    
//    @GetMapping
//    public List<OrdenMantenimiento> listar(){
//        return ordenMantenimientoService.listarOrdenes();
//    }
//    
//    @GetMapping("/{id}")
//    public OrdenMantenimiento buscarPorId(@PathVariable Long id){
//        return ordenMantenimientoService.obtenerPorId(id);
//    }
//    
//    @PutMapping("/{id}")
//    public OrdenMantenimiento actualizar(@PathVariable Long id, @RequestBody OrdenMantenimiento orden){
//        return ordenMantenimientoService.actualizarOrdenMantenimiento(id, orden);
//    }
//    
//    @DeleteMapping("/{id}/finalizar")
//    public void finalizar(@PathVariable Long id){
//        ordenMantenimientoService.finalizarOrdenMantenimiento(id);
//    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
    @PostMapping
    public ResponseEntity<OrdenDTO> crearOrden(@RequestBody OrdenDTO dto, 
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
                                                @RequestParam OrdenDTO dto){
        return ResponseEntity.ok(ordenMantenimientoService.actualizarOrden(id, dto));
    }
    
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<OrdenDTO> finalizarOrden(@PathVariable Long id){
        return ResponseEntity.ok(ordenMantenimientoService.finalizarOrden(id));
    }
}
