
package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.RepuestoDTO;
import java.util.List;
import org.springframework.stereotype.Service;


public interface RepuestoService {
    //ABM de repuestos
    RepuestoDTO crearRepuesto(RepuestoDTO dto);
    RepuestoDTO actualizarStock(Long idRepuesto, Integer nuevoStock);
    RepuestoDTO obtenerRepuestoPorId(Long id);
    List<RepuestoDTO> listarRepuestos();
    
    // alertar al supervisor cuando un repuesto se encuentra debajo del minimo
    List<RepuestoDTO> listarRepuestosConStockMinimo();
}

