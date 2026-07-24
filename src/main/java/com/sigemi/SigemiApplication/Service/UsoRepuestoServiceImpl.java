
package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.UsoRepuestoDTO;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Entidades.Repuesto;
import com.sigemi.SigemiApplication.Entidades.UsoRepuesto;
import com.sigemi.SigemiApplication.Excepciones.BusinessException;
import com.sigemi.SigemiApplication.Mapper.UsoRepuestoMapper;
import com.sigemi.SigemiApplication.Repository.OrdenMantenimientoRepository;
import com.sigemi.SigemiApplication.Repository.RepuestoRepository;
import com.sigemi.SigemiApplication.Repository.UsoRepuestoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class UsoRepuestoServiceImpl implements UsoRepuestoService {

    @Autowired
    private UsoRepuestoRepository usoRepuestoRepository;
    
    @Autowired
    private RepuestoRepository repuestoRepository;
    
    @Autowired
    private OrdenMantenimientoRepository ordenRepository;
    
    @Autowired
    private UsoRepuestoMapper mapper;
    
    // CU15: Registra el uso de un repuesto y descuenta el stock
    @Override
    public UsoRepuestoDTO registrarUso(UsoRepuestoDTO dto) {
        // 1. validar que la orden y el repuesto existan
        OrdenMantenimiento orden = ordenRepository.findById(dto.getOrdenId())
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con ID: " + dto.getOrdenId()));

        Repuesto repuesto = repuestoRepository.findById(dto.getRepuestoId())
                .orElseThrow(() -> new EntityNotFoundException("Repuesto no encontrado con ID: " + dto.getRepuestoId()));
        
        // 2. validar stock
        Integer cantidadUsada = dto.getCantidadUtilizada();
        if (repuesto.getStockActual().compareTo(cantidadUsada) < 0) {
            // No hay suficiente stock
            throw new BusinessException("Stock insuficiente para el repuesto: " + repuesto.getDescripcion() + 
                                        ". Stock actual: " + repuesto.getStockActual());
        }
    
        // 3. descontar el stock
        Integer stockActualizado = (repuesto.getStockActual() - cantidadUsada);
        repuesto.setStockActual(stockActualizado);
        repuestoRepository.save(repuesto);
        
        // 4. Crear la entidad de "UsoRepuesto"
        UsoRepuesto uso = new UsoRepuesto();
        uso.setOrden(orden);
        uso.setRepuesto(repuesto);
        uso.setCantidadUtilizada(cantidadUsada);
        
        UsoRepuesto guardado = usoRepuestoRepository.save(uso);
        
        return mapper.toDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsoRepuestoDTO> listarUsosPorOrden(Long idOrden) {
        return usoRepuestoRepository.findByOrden_IdOrden(idOrden).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
    
    
}
