package com.sigemi.SigemiApplication.Repository;

import com.sigemi.SigemiApplication.Entidades.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    
}
