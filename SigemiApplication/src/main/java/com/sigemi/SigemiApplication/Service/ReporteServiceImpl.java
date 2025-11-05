/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Service.java to edit this template
 */
package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.HistorialOrdenDTO;
import com.sigemi.SigemiApplication.DTO.TareaDTO;
import com.sigemi.SigemiApplication.DTO.UsoRepuestoDTO;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Mapper.OrdenMapper;
import com.sigemi.SigemiApplication.Repository.OrdenMantenimientoRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReporteServiceImpl implements ReporteService{

    @Autowired
    private OrdenMantenimientoRepository ordenRepository;
    
    // Inyectamos los servicios de Tareas y Usos (que ya tienen mappers)
    @Autowired
    private TareaMantenimientoService tareaService; //
    
    @Autowired
    private UsoRepuestoService usoRepuestoService; // (De la implementación anterior)

    @Autowired
    private OrdenMapper ordenMapper; // Para mapear la parte base de la orden

    @Override
    @Transactional(readOnly = true)
    public List<HistorialOrdenDTO> getHistorialPorEquipo(Long idEquipo) {
        
        // 1. Buscar todas las órdenes para ese equipo
        List<OrdenMantenimiento> ordenes = ordenRepository.findByEquipo_IdEquipo(idEquipo);
        
        List<HistorialOrdenDTO> historialCompleto = new ArrayList<>();

        // 2. Iterar cada orden y "enriquecerla"
        for (OrdenMantenimiento orden : ordenes) {
            HistorialOrdenDTO historialDTO = new HistorialOrdenDTO();

            //  Mapear datos basicos de la orden
            historialDTO.setIdOrden(orden.getIdOrden());
            historialDTO.setCodigoOrden(orden.getCodigoOrden());
            historialDTO.setTipo(orden.getTipo().name());
            historialDTO.setDescripcion(orden.getDescripcion());
            historialDTO.setPrioridad(orden.getPrioridad());
            historialDTO.setFechaCreacion(orden.getFechaCreacion());
            historialDTO.setFechaInicio(orden.getFechaInicio());
            historialDTO.setFechaFin(orden.getFechaFin());
            historialDTO.setEstado(orden.getEstado().name());
            
            // Datos del equipo
            if (orden.getEquipo() != null) {
                historialDTO.setEquipoId(orden.getEquipo().getIdEquipo());
                historialDTO.setEquipoNombre(orden.getEquipo().getNombre());
            }

            // Buscar Tareas asociadas (usando el servicio que ya las mapea a DTO)
            List<TareaDTO> tareasDTO = tareaService.listarPorOrden(orden.getIdOrden());
            historialDTO.setTareas(tareasDTO);

            // Buscar Repuestos Utilizados (usando el servicio que ya las mapea a DTO)
            List<UsoRepuestoDTO> repuestosDTO = usoRepuestoService.listarUsosPorOrden(orden.getIdOrden());
            historialDTO.setRepuestosUtilizados(repuestosDTO);
            
            // 3. Añadir el DTO completo a la lista
            historialCompleto.add(historialDTO);
        }

        // Ordenar por fecha de creación descendente (la más nueva primero)
        historialCompleto.sort((o1, o2) -> o2.getFechaCreacion().compareTo(o1.getFechaCreacion()));

        return historialCompleto;
    }
    
}
