
package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.FallaReportadaDTO;
import java.util.List;


public interface FallaService {
    FallaReportadaDTO reportarFalla(FallaReportadaDTO dto);
    List<FallaReportadaDTO> listarFallas();
    FallaReportadaDTO obtenerFallaPorId(Long id);
}
