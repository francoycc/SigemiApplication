package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.Entidades.Usuario;
import java.util.List;


public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario);
    Usuario actualizarUsuario(Long id, Usuario usuario);
    void deshabilitarUsuario(Long id);
    Usuario obtenerPorId(Long id);
    List<Usuario> listarUsuarios();
    Usuario obtenerPorNombreDeUsuario(String nombre);
}
