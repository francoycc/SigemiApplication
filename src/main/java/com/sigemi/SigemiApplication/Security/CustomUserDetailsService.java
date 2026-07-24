package com.sigemi.SigemiApplication.Security;

import com.sigemi.SigemiApplication.Entidades.Usuario;
import com.sigemi.SigemiApplication.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscamos el registro en la BD por el nombre de usuario
        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // 2. EXTRAEMOS EL ROL DIRECTO DE LA BD
        // usuario.getRol().name() nos dará exactamente "ADMINISTRADOR", "SUPERVISOR" u "OPERARIO"
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRol().name());

        // 3. Le pasamos el rol en limpio a Spring Security
        return new org.springframework.security.core.userdetails.User(
                usuario.getNombreUsuario(),
                usuario.getPassword(),
                Collections.singletonList(authority) // Asigna la autoridad limpia
        );
    }
}