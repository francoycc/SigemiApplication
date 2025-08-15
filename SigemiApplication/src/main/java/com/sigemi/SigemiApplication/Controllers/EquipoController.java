package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.EquipoDTO;
import com.sigemi.SigemiApplication.Entidades.Equipo;
import com.sigemi.SigemiApplication.Service.EquipoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {
    @Autowired
    private final EquipoService equipoService;
    
    public EquipoController(EquipoService eService){
        this.equipoService = eService;
    }

    @PostMapping
    public Equipo crear(@RequestBody Equipo equipo){
        return equipoService.crearEquipo(equipo);
    }
    
    @GetMapping
    public List<Equipo> listar(){
        return equipoService.listarEquipos();
    }
    
    @GetMapping("/{id}")
    public Equipo buscarPorId(@PathVariable Long id){
        return equipoService.obtenerPorId(id);
    }
    
    @PutMapping("/{id}")
    public Equipo actualizar(@PathVariable Long id, @RequestBody Equipo equipo){
        return equipoService.actualizarEquipo(id, equipo);
    }
    
    @DeleteMapping("/{id}")
    public void deshabilitar(@PathVariable Long id){
        equipoService.deshabilitarEquipo(id);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
    @PostMapping
    public ResponseEntity<EquipoDTO> crearEquipo(@RequestBody EquipoDTO dto) {
        EquipoDTO nuevoEquipo = equipoService.crearEquipo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEquipo);
    }
}
