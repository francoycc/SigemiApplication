package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.Entidades.Equipo;
import com.sigemi.SigemiApplication.Service.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/equipos")
@RequiredArgsConstructor
public class EquipoControllers {
    
    private final EquipoService equipoService;

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
    
    @GetMapping("/{id}")
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
}
