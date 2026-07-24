
package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.RepuestoDTO;
import com.sigemi.SigemiApplication.Entidades.Repuesto;
import com.sigemi.SigemiApplication.Mapper.RepuestoMapper;
import com.sigemi.SigemiApplication.Repository.RepuestoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class RepuestoServiceImpl implements RepuestoService{

    @Autowired
    private RepuestoRepository repuestoRepository;
    
    @Autowired
    private RepuestoMapper mapper;
    
    @Override
    @Transactional
    public RepuestoDTO crearRepuesto(RepuestoDTO dto) {
        Repuesto repuesto = new Repuesto();
        repuesto.setCodigo(dto.getCodigo());
        repuesto.setDescripcion(dto.getDescripcion());
        repuesto.setUnidadMedida(dto.getUnidadMedida());
        repuesto.setStockActual(dto.getStockActual());
        repuesto.setStockMinimo(dto.getStockMinimo());
        
        Repuesto guardado = repuestoRepository.save(repuesto);
        return mapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public RepuestoDTO actualizarStock(Long idRepuesto, Integer nuevoStock) {
        Repuesto repuesto = repuestoRepository.findById(idRepuesto)
                .orElseThrow(() -> new EntityNotFoundException("Repuesto no encontrado con ID: " + idRepuesto));
        
        repuesto.setStockActual(nuevoStock);
        Repuesto guardado = repuestoRepository.save(repuesto);
        return mapper.toDTO(guardado);    }

    @Override
    @Transactional(readOnly = true)
    public RepuestoDTO obtenerRepuestoPorId(Long id) {
        Repuesto repuesto = repuestoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Repuesto no encontrado con ID: " + id));
        return mapper.toDTO(repuesto);    
    }

    @Override
    public List<RepuestoDTO> listarRepuestos() {
        return repuestoRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RepuestoDTO> listarRepuestosConStockMinimo() {
        //  metodo que defini en el Repository para encontrar stock minimo
        List<Repuesto> repuestosAlertas = repuestoRepository.findRepuestosConStockBajo();
        
        return repuestosAlertas.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
    
}
