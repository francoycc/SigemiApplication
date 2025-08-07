
package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.Entidades.Equipo;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Service.OrdenMantenimientoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenMantenimientoControllers {
    
    private final OrdenMantenimientoService ordenMantenimientoService;

    @PostMapping
    public OrdenMantenimiento crear(@RequestBody OrdenMantenimiento orden){
        return ordenMantenimientoService.crearOrdenMantenimiento(orden);
    }
    
    @GetMapping
    public List<OrdenMantenimiento> listar(){
        return ordenMantenimientoService.listarOrdenes();
    }
    
    @GetMapping("/{id}")
    public OrdenMantenimiento buscarPorId(@PathVariable Long id){
        return ordenMantenimientoService.obtenerPorId(id);
    }
    
    @GetMapping("/{id}")
    public OrdenMantenimiento actualizar(@PathVariable Long id, @RequestBody OrdenMantenimiento orden){
        return ordenMantenimientoService.actualizarOrdenMantenimiento(id, orden);
    }
    
    @DeleteMapping("/{id}/finalizar")
    public void finalizar(@PathVariable Long id){
        ordenMantenimientoService.finalizarOrdenMantenimiento(id);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
}
