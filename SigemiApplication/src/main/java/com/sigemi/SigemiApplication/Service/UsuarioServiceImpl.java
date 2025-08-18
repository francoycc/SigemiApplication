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
import org.springframework.stereotype.Service;


@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private final UsuarioMapper mapper;
    
    public UsuarioServiceImpl(UsuarioRepository userRepository,
                        UsuarioMapper usuarioMap){
        this.usuarioRepository = userRepository;
        this.mapper = usuarioMap;
    }
    
//    @Override
//    public Usuario crearUsuario(Usuario usuario) {
//        return usuarioRepository.save(usuario);
//    }
//
//    @Override
//    public Usuario actualizarUsuario(Long id, Usuario usuario) {
//        Usuario actual = obtenerPorId(id);
//        actual.setEmail(usuario.getEmail());
//        actual.setPassword(usuario.getPassword());
//        actual.setTelefono(usuario.getTelefono());
//        actual.setTareas(usuario.getTareas());
//        return usuarioRepository.save(usuario);
//    }
//
//    @Override
//    public void deshabilitarUsuario(Long id) {
//        Usuario actual = obtenerPorId(id);
//        actual.setActivo(Boolean.FALSE);
//        usuarioRepository.save(actual);
//    }
//
//    @Override
//    public Usuario obtenerPorId(Long id) {
//        Usuario actual = usuarioRepository.findById(id)
//                .orElseThrow(()-> new EntityNotFoundException("Usuario no encontrado"));
//        return usuarioRepository.save(actual);
//    }
//
//    @Override
//    public List<Usuario> listarUsuarios() {
//        return usuarioRepository.findAll();
//    }
//
//    @Override
//    public Usuario obtenerPorNombreDeUsuario(String nombre) {
//        Usuario actual = usuarioRepository.findByNombreUsuario(nombre)
//                .orElseThrow(()-> new EntityNotFoundException("Usuario no encontrado"));
//        return usuarioRepository.save(actual);
//    }

    @Override
    public UsuarioDTO crearUsuario(UsuarioDTO usuariodto) {
        // validar usuario
        if(usuarioRepository.existByNombreUsuario(usuariodto.getNombreUsuario())){
            throw new BusinessException("Ya existe un usuario para el nombre ingresado.");
        }
        
        Usuario nuevo = mapper.toEntity(usuariodto);
        nuevo.setRol(RolUsuario.valueOf(usuariodto.getRol().toString()));
        nuevo.setActivo(Boolean.TRUE);
        Usuario guardado = usuarioRepository.save(nuevo);
        
        return mapper.toDTO(guardado);
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

        if (usuarios.isEmpty()) {
            System.out.println("No se encontraron Usuarios en la BD.");
        }

        System.out.println("Se encontraron Usuarios en la base de datos: " + usuarios.size());

        List<UsuarioDTO> usuariosDto = usuarios.stream()
            .map(equipo -> mapper.toDTO(equipo))
            .collect(Collectors.toList());

        return usuariosDto;
    }

    @Override
    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe el usuario para el ID ingresado: " + id));
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setRol(RolUsuario.valueOf(dto.getRol()));
        
        Usuario actualizado = usuarioRepository.save(usuario);
        return mapper.toDTO(actualizado);
    }

    @Override
    public UsuarioDTO obtenerPorNombreDeUsuario(String nombre) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombre)
                .orElseThrow(() -> new EntityNotFoundException("No existe el usuario para el NombreUsuario ingresado: " + nombre));
        return mapper.toDTO(usuario);
    }

    @Override
    public void deshabilitarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe el usuario para el ID ingresado: " + id));
        usuario.setActivo(Boolean.FALSE);
        usuarioRepository.save(usuario);
    }
    
    
}
