/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Service.java to edit this template
 */
package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.HistorialOrdenDTO;
import com.sigemi.SigemiApplication.DTO.TareaDTO;
import com.sigemi.SigemiApplication.DTO.UsoRepuestoDTO;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Mapper.TareaMapper;
import com.sigemi.SigemiApplication.Mapper.UsoRepuestoMapper;
import com.sigemi.SigemiApplication.Repository.OrdenMantenimientoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ReporteServiceImpl implements ReporteService{

    @Autowired
    private OrdenMantenimientoRepository ordenRepository;
    
    @Autowired
    private TareaMapper tareaMapper; 
    
    @Autowired
    private UsoRepuestoMapper usoRepuestoMapper; 

    @Override
    @Transactional(readOnly = true)
    public List<HistorialOrdenDTO> getHistorialPorEquipo(Long idEquipo) {
        
        // (Orden + Tareas + Repuestos)
        List<OrdenMantenimiento> ordenes = ordenRepository.findByEquipo_IdEquipo(idEquipo);
        
        List<HistorialOrdenDTO> historialCompleto = new ArrayList<>();

        // Mapeo en Memoria
        for (OrdenMantenimiento orden : ordenes) {
            HistorialOrdenDTO historialDTO = new HistorialOrdenDTO();

            // Datos básicos
            historialDTO.setIdOrden(orden.getIdOrden());
            historialDTO.setCodigoOrden(orden.getCodigoOrden());
            historialDTO.setTipo(orden.getTipo().name());
            historialDTO.setDescripcion(orden.getDescripcion());
            historialDTO.setPrioridad(orden.getPrioridad());
            historialDTO.setFechaCreacion(orden.getFechaCreacion());
            historialDTO.setFechaInicio(orden.getFechaInicio());
            historialDTO.setFechaFin(orden.getFechaFin());
            historialDTO.setEstado(orden.getEstado().name());
            
            if (orden.getEquipo() != null) {
                historialDTO.setEquipoId(orden.getEquipo().getIdEquipo());
                historialDTO.setEquipoNombre(orden.getEquipo().getNombre());
            }

            if (orden.getTareas() != null) {
                historialDTO.setTareas(
                    orden.getTareas().stream()
                        .map(tareaMapper::toDTO)
                        .collect(Collectors.toList())
                );
            }

            if (orden.getRepuestosUtilizados() != null) {
                historialDTO.setRepuestosUtilizados(
                    orden.getRepuestosUtilizados().stream()
                        .map(usoRepuestoMapper::toDTO)
                        .collect(Collectors.toList())
                );
            }
            
            historialCompleto.add(historialDTO);
        }

        // Ordenar en memoria
        historialCompleto.sort((o1, o2) -> o2.getFechaCreacion().compareTo(o1.getFechaCreacion()));

        return historialCompleto;
    }
    
}
