package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.EquipoDTO;
import com.sigemi.SigemiApplication.Entidades.Equipo;
import com.sigemi.SigemiApplication.Service.EquipoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor 
public class EquipoController {

    private final EquipoService equipoService;
    
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
    public ResponseEntity<Page<EquipoDTO>> listarEquipos(@PageableDefault(size = 10, sort = "nombre") 
            Pageable pageable) {
        return ResponseEntity.ok(equipoService.listarEquipos(pageable));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EquipoDTO> actualizar(@PathVariable Long id, 
            @Valid @RequestBody EquipoDTO dto) { 
       
        EquipoDTO actualizado = equipoService.actualizarEquipo(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        equipoService.desactivarEquipo(id);
        return ResponseEntity.noContent().build();
    }
}
