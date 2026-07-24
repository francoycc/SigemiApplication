package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.UsuarioDTO;
import com.sigemi.SigemiApplication.Security.JwtUtil;
import com.sigemi.SigemiApplication.Service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
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
    private final JwtUtil jwtUtil; // Inyectamos la utilidad
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales){
        String username = credenciales.get("username");
        String password = credenciales.get("password");
        
        try {
            UsuarioDTO usuario = usuarioService.obtenerPorNombreDeUsuario(username);
            
            if(usuario.getPassword().equals(password)) {
                usuario.setPassword(null);
                // Generamos el JWT con el username y el rol
                String token = jwtUtil.generateToken(usuario.getNombreUsuario(), usuario.getRol());
                
                // Empaquetamos todo
                Map<String, Object> response = new HashMap<>();
                response.put("user", usuario);
                response.put("token", token);
                
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta.");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
        }
    }
}