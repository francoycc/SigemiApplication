package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.UbicacionTecnicaDTO;
import com.sigemi.SigemiApplication.Entidades.UbicacionTecnica;
import com.sigemi.SigemiApplication.Enums.EstadoOperativo;
import com.sigemi.SigemiApplication.Excepciones.BusinessException;
import com.sigemi.SigemiApplication.Mapper.UbicacionMapper;
import com.sigemi.SigemiApplication.Repository.UbicacionTecnicaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UbicacionTecnicaServiceImpl implements UbicacionTecnicaService {
    
    private final UbicacionTecnicaRepository ubicacionRepository;
    private final UbicacionMapper mapper;
    @Autowired
    public UbicacionTecnicaServiceImpl(UbicacionTecnicaRepository ubiRepository,
            UbicacionMapper mapperUbicacion){
        this.ubicacionRepository = ubiRepository;
        this.mapper = mapperUbicacion;
    }

    @Override
    @Transactional
    public UbicacionTecnicaDTO crearUbicacion(UbicacionTecnicaDTO dto) {
        // validar ubicacion
        if(ubicacionRepository.existsByCodigo(dto.getCodigo())){
            throw new BusinessException("Ya existe una ubicacion para el codigo ingresado.");
        }
        
        UbicacionTecnica ubicacion = mapper.toEntity(dto);
        ubicacion.setEstado(EstadoOperativo.valueOf(dto.getEstado()));
        
        if (dto.getIdPadre() != null) {
            // Si trae ID padre, lo buscamos y asignamos
            UbicacionTecnica ubicacionPadre = ubicacionRepository.findById(dto.getIdPadre())
                .orElseThrow(() -> new EntityNotFoundException("Ubicación padre no encontrada"));
            ubicacion.setUbicacionPadre(ubicacionPadre);
        } else {
            // Si NO trae ID padre, es una raíz (Planta). Se deja null explícitamente.
            ubicacion.setUbicacionPadre(null);
        }
        
        UbicacionTecnica guardada = ubicacionRepository.save(ubicacion);
        
        return mapper.toDTO(guardada);
    }

    @Override
    @Transactional
    public UbicacionTecnicaDTO obtenerPorId(Long id) {
        UbicacionTecnica ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ubicacion Tecnica no encontrado con ID: " + id));
        
        return mapper.toDTO(ubicacion);
    }

    @Override
    public List<UbicacionTecnicaDTO> listarUbicaciones() {
        List<UbicacionTecnica> ubicaciones = ubicacionRepository.findAll();
        
        if (ubicaciones.isEmpty()) {
            System.out.println("No se encontraron equipos en la BD.");
        }
        List<UbicacionTecnicaDTO> ubicacionesDto = ubicaciones.stream()
            .map(ubicacion -> mapper.toDTO(ubicacion))
            .collect(Collectors.toList());
        return ubicacionesDto;
    }

    @Override
    public List<UbicacionTecnicaDTO> listarUbicacionesPorPadre(Long idPadre) {
        List<UbicacionTecnica> ubicaciones;
        
        if (idPadre == null) {
            ubicaciones = ubicacionRepository.findByUbicacionPadreIsNull();
        } else {
            // buscamos sus hijos 
            ubicaciones = ubicacionRepository.findByUbicacionPadre_IdUbicacion(idPadre);
        }

        // Convertimos la lista de Entidades a DTOs
        return ubicaciones.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public UbicacionTecnicaDTO actualizarUbicacion(Long id, UbicacionTecnicaDTO dto) {
        UbicacionTecnica ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ubicación no encontrada con ID: " + id));

        ubicacion.setNombre(dto.getNombre());
        ubicacion.setEstado(EstadoOperativo.valueOf(dto.getEstado()));
        ubicacion.setTipo(dto.getTipo());
        
        UbicacionTecnica guardada = ubicacionRepository.save(ubicacion);
        return mapper.toDTO(guardada);
    }

    @Override
    @Transactional
    public void desactivarUbicacion(Long id) {
        UbicacionTecnica ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ubicación no encontrada con ID: " + id));

        ubicacion.setEstado(EstadoOperativo.FueraDeServicio);
        ubicacionRepository.save(ubicacion);
    }
    
}
