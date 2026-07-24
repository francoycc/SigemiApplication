
package com.sigemi.SigemiApplication.Repository;

import com.sigemi.SigemiApplication.Entidades.FallaReportada;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface FallaReportadaRepository extends JpaRepository<FallaReportada, Long> {
    List<FallaReportada> findByEquipo_IdEquipo(Long idEquipo);
    
    @Query("SELECT COUNT(f) FROM FallaReportada f WHERE f.fechaReporte BETWEEN :inicio AND :fin")
    Long contarFallasEnPeriodo(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);
}
