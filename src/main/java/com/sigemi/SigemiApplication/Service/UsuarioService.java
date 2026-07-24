package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.UsuarioDTO;
import com.sigemi.SigemiApplication.Entidades.Usuario;
import java.util.List;


public interface UsuarioService {
//    Usuario crearUsuario(Usuario usuario);
//    Usuario actualizarUsuario(Long id, Usuario usuario);
//    void deshabilitarUsuario(Long id);
//    Usuario obtenerPorId(Long id);
//    List<Usuario> listarUsuarios();
//    Usuario obtenerPorNombreDeUsuario(String nombre);
    UsuarioDTO crearUsuario(UsuarioDTO usuario);
    UsuarioDTO obtenerPorId(Long id);
    List<UsuarioDTO> listarUsuarios();
    UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuario);
    UsuarioDTO obtenerPorNombreDeUsuario(String nombre);
    void deshabilitarUsuario(Long id);
}
