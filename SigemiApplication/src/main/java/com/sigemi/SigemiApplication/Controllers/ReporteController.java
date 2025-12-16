
package com.sigemi.SigemiApplication.Controllers;

import com.sigemi.SigemiApplication.DTO.HistorialOrdenDTO;
import com.sigemi.SigemiApplication.Service.ReporteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/reportes")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    /**
     * CU28: Consultar historial de mantenimiento por equipo
     */
    @GetMapping("/historial/equipo/{idEquipo}")
    public ResponseEntity<List<HistorialOrdenDTO>> getHistorialPorEquipo(@PathVariable Long idEquipo) {
        List<HistorialOrdenDTO> historial = reporteService.getHistorialPorEquipo(idEquipo);
        return ResponseEntity.ok(historial);
    }
}
