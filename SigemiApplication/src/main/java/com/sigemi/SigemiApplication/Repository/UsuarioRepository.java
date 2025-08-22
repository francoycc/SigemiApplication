package com.sigemi.SigemiApplication.Repository;

import com.sigemi.SigemiApplication.Entidades.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    boolean existByEmail(String email);
}
