package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.EquipoDTO;
import com.sigemi.SigemiApplication.Entidades.Equipo;
import com.sigemi.SigemiApplication.Entidades.UbicacionTecnica;
import com.sigemi.SigemiApplication.Enums.Criticidad;
import com.sigemi.SigemiApplication.Enums.EstadoOperativo;
import com.sigemi.SigemiApplication.Excepciones.BusinessException;
import com.sigemi.SigemiApplication.Mapper.EquipoMapper;
import com.sigemi.SigemiApplication.Repository.EquipoRepository;
import com.sigemi.SigemiApplication.Repository.UbicacionTecnicaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EquipoServiceImpl implements EquipoService {
    
    private EquipoRepository equipoRepository;
    private UbicacionTecnicaRepository ubicacionRepository;
    private EquipoMapper mapper;

    @Override
    @Transactional
    public EquipoDTO crearEquipo(EquipoDTO dto){
        log.info("Intentando crear equipo con código: {}", dto.getCodigoEquipo());
        // validar equipo
        if(equipoRepository.existsByCodigoEquipo(dto.getCodigoEquipo())){
            throw new BusinessException("Ya existe un equipo para el codigo ingresado.");
        }
        // valido equipo por numero de serie
        if(dto.getNumeroSerie()!= null && equipoRepository.existsByNumeroSerie(dto.getNumeroSerie())){
            throw new BusinessException("Ya existe un equipo para el numero de serie ingresado.");
        }
        //traigo ubicacion
        UbicacionTecnica ubicacion = ubicacionRepository.findById(dto.getUbicacionTecnicaId())
                .orElseThrow(() -> new EntityNotFoundException("Ubicación técnica no encontrada"));
        
        Equipo equipo = mapper.toEntity(dto);
        
        equipo.setUbicacionTecnica(ubicacion);
        equipo.setActivo(Boolean.TRUE);
        
        try {
            equipo.setEstadoOperativo(EstadoOperativo.valueOf(dto.getEstadoOperativo()));
            equipo.setCriticidad(Criticidad.valueOf(dto.getCriticidad()));
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Valor de Estado Operativo o Criticidad inválido.");
        }
        
        Equipo guardado = equipoRepository.save(equipo);
        log.info("Equipo creado exitosamente con ID: {}", guardado.getIdEquipo());
        
        return mapper.toDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public EquipoDTO buscarPorId(Long id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado con ID: " + id));
        return mapper.toDTO(equipo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EquipoDTO> listarEquipos(Pageable pageable) {
        log.debug("Listando equipos paginados");
        Page<EquipoDTO> equipos = equipoRepository.findAll(pageable)
                .map(mapper::toDTO);
        
        if (equipos.isEmpty()) {
            log.debug("No se encontraron equipos en la BD.");
        }
        return equipos;
    }

    @Override
    @Transactional
    public EquipoDTO actualizarEquipo(Long id, EquipoDTO dto) {
        log.info("Actualizando equipo ID: {}", id);
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encuentra el equipo para el ID: " + id));
        
        equipo.setNombre(dto.getNombre());
        equipo.setMarca(dto.getMarca());
        equipo.setModelo(dto.getModelo());
        equipo.setTipo(dto.getTipo());
        equipo.setFrecuencia(dto.getFrecuencia());
        equipo.setNumeroSerie(dto.getNumeroSerie()); 
        
        if (dto.getCriticidad() != null) {
            equipo.setCriticidad(Criticidad.valueOf(dto.getCriticidad()));
        }
        if (dto.getEstadoOperativo() != null) {
            equipo.setEstadoOperativo(EstadoOperativo.valueOf(dto.getEstadoOperativo()));
        }
        
        if (dto.getUbicacionTecnicaId() != null && 
            !dto.getUbicacionTecnicaId().equals(equipo.getUbicacionTecnica().getIdUbicacion())) {
            
            UbicacionTecnica nuevaUbicacion = ubicacionRepository.findById(dto.getUbicacionTecnicaId())
                    .orElseThrow(() -> new EntityNotFoundException("Nueva ubicación no encontrada"));
            equipo.setUbicacionTecnica(nuevaUbicacion);
        }
        
        Equipo guardado = equipoRepository.save(equipo);
        return mapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public void desactivarEquipo(Long id) {
        Equipo desactivado = equipoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe equipo para el ID: " + id));
        
        desactivado.setEstadoOperativo(EstadoOperativo.FueraDeServicio);
        desactivado.setActivo(false);
        
        equipoRepository.save(desactivado);
    }
    
    
}
