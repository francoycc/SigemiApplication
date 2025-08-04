package Controllers;

import Entidades.Equipo;
import Service.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
    
    @GetMapping
    public Equipo buscarPorId(@PathVariable Long id){
        return equipoService.obtenerPorId(id);
    }
    
    @PutMapping
    public Equipo actualizar(@PathVariable Long id, @RequestBody Equipo equipo){
        return equipoService.actualizarEquipo(id, equipo);
    }
    
    @DeleteMapping("/{id}")
    public void deshabilitar(@PathVariable Long id){
        equipoService.deshabilitarEquipo(id);
    }
}
