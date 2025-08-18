package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.UsuarioDTO;
import com.sigemi.SigemiApplication.Entidades.Usuario;
import com.sigemi.SigemiApplication.Service.UsuarioService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    @Autowired
    private final UsuarioService usuarioService;
    
    public UsuarioController(UsuarioService userService){
        this.usuarioService = userService;
    }
//    @PostMapping
//    public Usuario crear(@RequestBody Usuario usuario) {
//        return usuarioService.crearUsuario(usuario);
//    }
//
//    @PutMapping("/{id}")
//    public Usuario actualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
//        return usuarioService.actualizarUsuario(id, usuario);
//    }
//
//    @PutMapping("/deshabilitar/{id}")
//    public void deshabilitar(@PathVariable Long id) {
//        usuarioService.deshabilitarUsuario(id);
//    }
//
//    @GetMapping("/{id}")
//    public Usuario obtenerPorId(@PathVariable Long id) {
//        return usuarioService.obtenerPorId(id);
//    }
//    
//    @GetMapping("/{nombre}")
//    public Usuario obtenerPorNombreDeUsuario(@PathVariable String nombre){
//        return usuarioService.obtenerPorNombreDeUsuario(nombre);
//    }
//
//    @GetMapping
//    public List<Usuario> listar() {
//        return usuarioService.listarUsuarios();
//    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioDTO dto) {
        UsuarioDTO nuevoUsuario = usuarioService.crearUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }
    
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        UsuarioDTO actualizado = usuarioService.actualizarUsuario(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(actualizado);
    }

    @DeleteMapping("/deshabilitar/{id}")
    public ResponseEntity<Void> deshabilitar(@PathVariable Long id) {
        usuarioService.deshabilitarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
