package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.UsuarioDTO;
import com.sigemi.SigemiApplication.Service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    
    private final UsuarioService usuarioService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales){
        String username = credenciales.get("username");
        String password = credenciales.get("password");
        
        try{
            UsuarioDTO usuario = usuarioService.obtenerPorNombreDeUsuario(username);
            
            if(usuario.getPassword().equals(password)){
                usuario.setPassword(null);
                return ResponseEntity.ok(usuario);
            }else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta.");
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encoentrado.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor: " + e.getMessage());
        }
        
    }
}
