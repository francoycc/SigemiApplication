package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.EquipoDTO;
import com.sigemi.SigemiApplication.Entidades.Equipo;
import com.sigemi.SigemiApplication.Service.EquipoService;
import jakarta.validation.Valid;
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

//    @PostMapping
//    public Equipo crear(@RequestBody Equipo equipo){
//        return equipoService.crearEquipo(equipo);
//    }
//    
//    @GetMapping
//    public List<Equipo> listar(){
//        return equipoService.listarEquipos();
//    }
//    
//    @GetMapping("/{id}")
//    public Equipo buscarPorId(@PathVariable Long id){
//        return equipoService.obtenerPorId(id);
//    }
//    
//    @PutMapping("/{id}")
//    public Equipo actualizar(@PathVariable Long id, @RequestBody Equipo equipo){
//        return equipoService.actualizarEquipo(id, equipo);
//    }
//    
//    @DeleteMapping("/{id}")
//    public void deshabilitar(@PathVariable Long id){
//        equipoService.deshabilitarEquipo(id);
//    }

    
    @PostMapping
    public ResponseEntity<EquipoDTO> crearEquipo(@Valid @RequestBody EquipoDTO dto) {
        EquipoDTO nuevoEquipo = equipoService.crearEquipo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEquipo);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EquipoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(equipoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<EquipoDTO>> listarEquipos() {
        return ResponseEntity.ok(equipoService.listarEquipos());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EquipoDTO> actualizar(@Valid @PathVariable Long id, 
            @RequestBody EquipoDTO dto) {
        EquipoDTO actualizado = equipoService.actualizarEquipo(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        equipoService.desactivarEquipo(id);
        return ResponseEntity.noContent().build();
    }
}
