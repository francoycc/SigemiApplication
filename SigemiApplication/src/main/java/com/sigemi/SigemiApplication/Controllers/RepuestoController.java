/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/RestController.java to edit this template
 */
package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.RepuestoDTO;
import com.sigemi.SigemiApplication.Service.RepuestoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {
    
    @Autowired
    private RepuestoService repuestoService;
    
    @GetMapping
    public ResponseEntity<List<RepuestoDTO>> listar() {
        return ResponseEntity.ok(repuestoService.listarRepuestos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RepuestoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(repuestoService.obtenerRepuestoPorId(id));
    }
    
    // listar stock critico con "/alertas"
    @GetMapping("/alertas")
    public ResponseEntity<List<RepuestoDTO>> listarRepuestosConStockMinimo() {
        return ResponseEntity.ok(repuestoService.listarRepuestosConStockMinimo());
    }
    
    @PostMapping
    public ResponseEntity<RepuestoDTO> crear(@Valid @RequestBody RepuestoDTO dto) {
        RepuestoDTO nuevo = repuestoService.crearRepuesto(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    
    /*
    @DeleteMapping("/{id}")
    ResponseEntity<Void> eliminarRepuesto(@PathVariable Long id) {
        repuestoService.eliminarRepuesto(id);
        return ResponseEntity.noContent().build();
    }
    */
    
}
