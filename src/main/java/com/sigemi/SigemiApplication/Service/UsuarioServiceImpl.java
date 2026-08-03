package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.UsuarioDTO;
import com.sigemi.SigemiApplication.Entidades.Usuario;
import com.sigemi.SigemiApplication.Enums.RolUsuario;
import com.sigemi.SigemiApplication.Excepciones.BusinessException;
import com.sigemi.SigemiApplication.Mapper.UsuarioMapper;
import com.sigemi.SigemiApplication.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper mapper;
    
    // Se inyecta el PasswordEncoder de Spring Security para el manejo de credenciales
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UsuarioServiceImpl(UsuarioRepository userRepository, UsuarioMapper usuarioMap, PasswordEncoder passwordEncoder){
        this.usuarioRepository = userRepository;
        this.mapper = usuarioMap;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UsuarioDTO crearUsuario(UsuarioDTO usuariodto) {
        // Validar usuario
        if(usuarioRepository.existsByNombreUsuario(usuariodto.getNombreUsuario())){
            throw new BusinessException("Ya existe un usuario para el nombre ingresado.");
        }
        if(usuarioRepository.existsByEmail(usuariodto.getEmail())){
            throw new BusinessException("Ya existe un usuario para el email ingresado.");
        }
        
        Usuario nuevo = mapper.toEntity(usuariodto);
        nuevo.setRol(RolUsuario.valueOf(usuariodto.getRol().toUpperCase()));
        nuevo.setActivo(Boolean.TRUE);
        
        // Encriptar la contraseña antes de persistir
        nuevo.setPassword(passwordEncoder.encode(usuariodto.getPassword())); 
        
        Usuario guardado = usuarioRepository.save(nuevo);
        return mapper.toDTO(guardado);
    }

    @Override
    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe el usuario para el ID ingresado: " + id));
        
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setRol(RolUsuario.valueOf(dto.getRol().toUpperCase()));
        
        // actualizo condicional del Estado Activo 
        if (dto.getActivo() != null) {
            usuario.setActivo(dto.getActivo());
        }

        // actualizo condicional de Contraseña
        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        
        Usuario actualizado = usuarioRepository.save(usuario);
        return mapper.toDTO(actualizado);
    }

    @Override
    public void deshabilitarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe el usuario para el ID ingresado: " + id));
        usuario.setActivo(Boolean.FALSE);
        usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe el usuario para el ID ingresado: " + id));
        return mapper.toDTO(usuario);
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    public UsuarioDTO obtenerPorNombreDeUsuario(String nombre) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombre)
                .orElseThrow(() -> new EntityNotFoundException("No existe el usuario para el NombreUsuario ingresado: " + nombre));
        return mapper.toDTO(usuario);
    }
}