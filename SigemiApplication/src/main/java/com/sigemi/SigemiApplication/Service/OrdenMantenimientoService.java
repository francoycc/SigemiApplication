package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.OrdenDTO;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import java.util.List;


public interface OrdenMantenimientoService {
    OrdenMantenimiento crearOrdenMantenimiento(OrdenMantenimiento orden);
    List<OrdenMantenimiento> listarOrdenes();
    OrdenMantenimiento obtenerPorId(Long id);
    OrdenMantenimiento actualizarOrdenMantenimiento(Long id, OrdenMantenimiento orden);
    void finalizarOrdenMantenimiento(Long id);
    OrdenDTO crearOrden(OrdenDTO dto, String usuarioSupervisor);
}
