package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.OrdenDTO;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface OrdenMantenimientoService {
//    OrdenMantenimiento crearOrdenMantenimiento(OrdenMantenimiento orden);
//    List<OrdenMantenimiento> listarOrdenes();
//    OrdenMantenimiento obtenerPorId(Long id);
//    OrdenMantenimiento actualizarOrdenMantenimiento(Long id, OrdenMantenimiento orden);
//    void finalizarOrdenMantenimiento(Long id);
    OrdenDTO crearOrden(OrdenDTO dto, String usuarioSupervisor);
    
    OrdenDTO obtenerPorId(Long id);

    List<OrdenDTO> listarOrdenes();
    
    Page<OrdenDTO> listarPorEquipo(Long idEquipo, Pageable pageable);

    OrdenDTO actualizarOrden(Long id, OrdenDTO dto);

    OrdenDTO finalizarOrden(Long id);
}
