package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.Entidades.Usuario;
import com.sigemi.SigemiApplication.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;
    
    public UsuarioServiceImpl(UsuarioRepository userRepository){
        this.usuarioRepository = userRepository;
    }
    
    @Override
    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario actual = obtenerPorId(id);
        actual.setEmail(usuario.getEmail());
        actual.setPassword(usuario.getPassword());
        actual.setTelefono(usuario.getTelefono());
        actual.setTareas(usuario.getTareas());
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deshabilitarUsuario(Long id) {
        Usuario actual = obtenerPorId(id);
        actual.setActivo(Boolean.FALSE);
        usuarioRepository.save(actual);
    }

    @Override
    public Usuario obtenerPorId(Long id) {
        Usuario actual = usuarioRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Usuario no encontrado"));
        return usuarioRepository.save(actual);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerPorNombreDeUsuario(String nombre) {
        Usuario actual = usuarioRepository.findByNombreUsuario(nombre)
                .orElseThrow(()-> new EntityNotFoundException("Usuario no encontrado"));
        return usuarioRepository.save(actual);
    }
    
}
