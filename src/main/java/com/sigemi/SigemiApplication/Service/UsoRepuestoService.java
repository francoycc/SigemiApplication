
package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.UsoRepuestoDTO;
import java.util.List;


public interface UsoRepuestoService {
   
    // CU15: Registrar un uso de repuestos en una orden
    UsoRepuestoDTO registrarUso(UsoRepuestoDTO dto);
    
    
    // para CU28 (Historial) y CU29 (Costos)
    List<UsoRepuestoDTO> listarUsosPorOrden(Long idOrden); 
}
