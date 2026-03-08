package com.sigemi.SigemiApplication.Repository;

import com.sigemi.SigemiApplication.Entidades.Equipo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    boolean existsByCodigoEquipo(String codigoEquipo);
    boolean existsByNumeroSerie(String numeroSerie);
    
    @EntityGraph(attributePaths = {"ubicacionTecnica"})
    Page<Equipo> findAll(Pageable pageable);
}
