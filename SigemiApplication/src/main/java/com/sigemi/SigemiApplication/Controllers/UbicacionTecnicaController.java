
package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.UbicacionTecnicaDTO;
import com.sigemi.SigemiApplication.Entidades.UbicacionTecnica;
import com.sigemi.SigemiApplication.Service.UbicacionTecnicaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionTecnicaController {
    
    @Autowired
    private final UbicacionTecnicaService ubicacionService;

    public UbicacionTecnicaController(UbicacionTecnicaService uService){
        this.ubicacionService = uService;
    }
//    @PostMapping
//    public UbicacionTecnica crear(@RequestBody UbicacionTecnica ubicacion) {
//        return ubicacionService.crearUbicacion(ubicacion);
//    }
//
//    @PutMapping("/{id}")
//    public UbicacionTecnica modificar(@PathVariable Long id, @RequestBody UbicacionTecnica ubicacion) {
//        return ubicacionService.modificarUbicacion(id, ubicacion);
//    }
//
//    @PutMapping("/desactivar/{id}")
//    public void desactivar(@PathVariable Long id) {
//        ubicacionService.desactivarUbicacion(id);
//    }
//
//    @GetMapping
//    public List<UbicacionTecnica> listar() {
//        return ubicacionService.listarUbicaciones();
//    }
//
//    @GetMapping("/{id}")
//    public UbicacionTecnica obtenerPorId(@PathVariable Long id) {
//        return ubicacionService.obtenerPorId(id);
//    }
//    @GetMapping("/{idPadre}")
//    public List<UbicacionTecnica> obtenerPorIdPadre(@PathVariable Long idPadre) {
//        return ubicacionService.listarUbicacionesPorPadre(idPadre);
//    }
    
     
    @PostMapping
    public ResponseEntity<UbicacionTecnicaDTO> crearUbicacionTecnica(@RequestBody UbicacionTecnicaDTO dto) {
        UbicacionTecnicaDTO nuevoUbicacion = ubicacionService.crearUbicacion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUbicacion);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UbicacionTecnicaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ubicacionService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<UbicacionTecnicaDTO>> listarUbicaciones() {
        return ResponseEntity.ok(ubicacionService.listarUbicaciones());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UbicacionTecnicaDTO> actualizar(@PathVariable Long id, 
            @RequestBody UbicacionTecnicaDTO dto) {
        UbicacionTecnicaDTO actualizada = ubicacionService.actualizarUbicacion(id, dto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        ubicacionService.desactivarUbicacion(id);
        return ResponseEntity.noContent().build();
    }
}
