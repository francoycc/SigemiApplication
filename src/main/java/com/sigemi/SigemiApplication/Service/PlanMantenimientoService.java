
package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.PlanMantenimientoDTO;
import java.util.List;

public interface PlanMantenimientoService {
    
    PlanMantenimientoDTO crearPlan(PlanMantenimientoDTO dto);

    PlanMantenimientoDTO obtenerPorId(Long id);
    
    List<PlanMantenimientoDTO> listarPlanes();
    
    List<PlanMantenimientoDTO> listarPlanesPorEquipo(Long idEquipo);

    PlanMantenimientoDTO actualizarPlan(Long id, PlanMantenimientoDTO dto);
    
    void desactivarPlan(Long id);

    // Logica para generar automaticamente ordenes preventivas
    void generarOrdenesAutomaticas();
}
