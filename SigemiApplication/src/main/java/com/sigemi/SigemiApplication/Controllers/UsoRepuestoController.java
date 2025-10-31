/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Controller.java to edit this template
 */
package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.UsoRepuestoDTO;
import com.sigemi.SigemiApplication.Service.UsoRepuestoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usorepuestos")
public class UsoRepuestoController {
    
    @Autowired
    private UsoRepuestoService usoRepuestoService;
    
    // CU15: registrar uso de respuestos en orden
    @PostMapping
    public ResponseEntity<UsoRepuestoDTO> registrarUso(@RequestBody UsoRepuestoDTO dto) {
        UsoRepuestoDTO nuevoUso = usoRepuestoService.registrarUso(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUso);
    }
    
    //Para CU28/CU29: Ver repuestos usados en una orden
    @GetMapping("/orden/{idOrden}")
    public ResponseEntity<List<UsoRepuestoDTO>> listarPorOrden(@PathVariable Long idOrden) {
        return ResponseEntity.ok(usoRepuestoService.listarUsosPorOrden(idOrden));
    }
    
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
}
