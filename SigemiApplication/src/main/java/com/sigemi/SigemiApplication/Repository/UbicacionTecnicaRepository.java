package com.sigemi.SigemiApplication.Repository;

import com.sigemi.SigemiApplication.Entidades.UbicacionTecnica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UbicacionTecnicaRepository extends JpaRepository<UbicacionTecnica, Long> {
    Boolean existsByCodigo(String codigo);
    List<UbicacionTecnica> findByUbicacionPadre_IdUbicacion(Long idUbicacionTecnicaPadre);
}
