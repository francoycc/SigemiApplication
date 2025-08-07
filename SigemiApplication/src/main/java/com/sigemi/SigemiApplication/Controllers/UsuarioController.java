package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.Entidades.Usuario;
import com.sigemi.SigemiApplication.Service.UsuarioService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    @PutMapping("/deshabilitar/{id}")
    public void deshabilitar(@PathVariable Long id) {
        usuarioService.deshabilitarUsuario(id);
    }

    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id);
    }
    
    @GetMapping("/{nombre}")
    public Usuario obtenerPorNombreDeUsuario(@PathVariable String nombre){
        return usuarioService.obtenerPorNombreDeUsuario(nombre);
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarUsuarios();
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
}
