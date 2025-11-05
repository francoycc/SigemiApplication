
package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.HistorialOrdenDTO;
import java.util.List;



public interface ReporteService {
    List<HistorialOrdenDTO> getHistorialPorEquipo(Long idEquipo);
}
