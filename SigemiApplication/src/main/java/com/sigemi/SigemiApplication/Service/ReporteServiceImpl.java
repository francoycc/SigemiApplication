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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    public Page<HistorialOrdenDTO> getHistorialPorEquipo(Long idEquipo, Pageable pageable) {
        
        Page<OrdenMantenimiento> paginaOrdenes = ordenRepository.findByEquipo_IdEquipo(idEquipo, pageable);
        
        return paginaOrdenes.map(orden -> {
            HistorialOrdenDTO dto = new HistorialOrdenDTO();
            
            // Mapeo manual 
            dto.setIdOrden(orden.getIdOrden());
            dto.setCodigoOrden(orden.getCodigoOrden());
            dto.setTipo(orden.getTipo().name());
            dto.setDescripcion(orden.getDescripcion());
            dto.setPrioridad(orden.getPrioridad());
            dto.setFechaCreacion(orden.getFechaCreacion());
            dto.setFechaInicio(orden.getFechaInicio());
            dto.setFechaFin(orden.getFechaFin());
            dto.setEstado(orden.getEstado().name());

            if (orden.getEquipo() != null) {
                dto.setEquipoId(orden.getEquipo().getIdEquipo());
                dto.setEquipoNombre(orden.getEquipo().getNombre());
            }

            // Usamos los mappers para las colecciones anidadas
            if (orden.getTareas() != null) {
                dto.setTareas(orden.getTareas().stream()
                        .map(tareaMapper::toDTO)
                        .collect(Collectors.toList()));
            }

            if (orden.getRepuestosUtilizados() != null) {
                dto.setRepuestosUtilizados(orden.getRepuestosUtilizados().stream()
                        .map(usoRepuestoMapper::toDTO)
                        .collect(Collectors.toList()));
            }

            return dto;
        });
    }
}
