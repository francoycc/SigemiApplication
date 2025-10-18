
package com.sigemi.SigemiApplication.Repository;

import com.sigemi.SigemiApplication.Entidades.FallaReportada;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FallaReportadaRepository extends JpaRepository<FallaReportada, Long> {
    List<FallaReportada> findByEquipo_IdEquipo(Long idEquipo);
}
