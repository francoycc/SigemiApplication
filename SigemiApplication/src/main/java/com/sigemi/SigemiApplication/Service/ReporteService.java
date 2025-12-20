
package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.HistorialOrdenDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface ReporteService {
    public Page<HistorialOrdenDTO> getHistorialPorEquipo(Long idEquipo, Pageable pageable);
}
