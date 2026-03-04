package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.UbicacionTecnicaDTO;
import com.sigemi.SigemiApplication.Entidades.UbicacionTecnica;
import com.sigemi.SigemiApplication.Enums.EstadoOperativo;
import com.sigemi.SigemiApplication.Excepciones.BusinessException;
import com.sigemi.SigemiApplication.Mapper.UbicacionMapper;
import com.sigemi.SigemiApplication.Repository.UbicacionTecnicaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j; 
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j 
@Service
public class UbicacionTecnicaServiceImpl implements UbicacionTecnicaService {
    
    private final UbicacionTecnicaRepository ubicacionRepository;
    private final UbicacionMapper mapper;

    @Autowired
    public UbicacionTecnicaServiceImpl(UbicacionTecnicaRepository ubiRepository, UbicacionMapper mapperUbicacion){
        this.ubicacionRepository = ubiRepository;
        this.mapper = mapperUbicacion;
    }

    private EstadoOperativo parsearEstado(String estadoStr) {
        try {
            return EstadoOperativo.valueOf(estadoStr);
        } catch (IllegalArgumentException e) {
            log.warn("Intento de asignar estado inválido: {}", estadoStr);
            throw new BusinessException("El estado '" + estadoStr + "' no es un estado operativo válido.");
        }
    }

    @Override
    @Transactional
    public UbicacionTecnicaDTO crearUbicacion(UbicacionTecnicaDTO dto) {
        log.info("Iniciando creación de Ubicación Técnica. Código: {}", dto.getCodigo());

        if(ubicacionRepository.existsByCodigo(dto.getCodigo())){
            log.warn("Fallo al crear: Ya existe ubicación con código {}", dto.getCodigo());
            throw new BusinessException("Ya existe una ubicación registrada con el código: " + dto.getCodigo());
        }
        
        UbicacionTecnica ubicacion = mapper.toEntity(dto);
        ubicacion.setEstado(parsearEstado(dto.getEstado()));
        
        if (dto.getIdPadre() != null) {
            UbicacionTecnica ubicacionPadre = ubicacionRepository.findById(dto.getIdPadre())
                .orElseThrow(() -> {
                    log.error("Fallo al crear: No se encontró padre con ID {}", dto.getIdPadre());
                    return new EntityNotFoundException("No se encontró la Ubicación padre especificada.");
                });
            ubicacion.setUbicacionPadre(ubicacionPadre);
            log.debug("Se asignó la ubicación padre ID: {}", dto.getIdPadre());
        } else {
            ubicacion.setUbicacionPadre(null);
            log.debug("La ubicación se creará como nodo Raíz (Sin padre).");
        }
        
        UbicacionTecnica guardada = ubicacionRepository.save(ubicacion);
        log.info("Ubicación Técnica creada exitosamente. ID asignado: {}", guardada.getIdUbicacion());
        return mapper.toDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public UbicacionTecnicaDTO obtenerPorId(Long id) {
        log.info("Consultando detalle de Ubicación Técnica ID: {}", id);
        UbicacionTecnica ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Consulta fallida: Ubicación ID {} no existe", id);
                    return new EntityNotFoundException("Ubicacion Técnica no encontrada con ID: " + id);
                });
        return mapper.toDTO(ubicacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UbicacionTecnicaDTO> listarUbicaciones() {
        log.info("Consultando listado de ubicaciones tecnicas");
        List<UbicacionTecnica> ubicaciones = ubicacionRepository.findAll();
        
        if (ubicaciones.isEmpty()) {
            log.warn("La consulta devolvio 0 resultados. La tabla de ubicaciones está vacía.");
        } else {
            log.debug("Se recuperaron {} ubicaciones de la base de datos.", ubicaciones.size());
        }
        
        return ubicaciones.stream()
            .map(mapper::toDTO)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UbicacionTecnicaDTO> listarUbicacionesPorPadre(Long idPadre) {
        log.info("Consultando jerarquía de ubicaciones. ID Padre: {}", idPadre != null ? idPadre : "RAIZ");
        
        List<UbicacionTecnica> ubicaciones;
        if(idPadre == null){
            ubicaciones = ubicacionRepository.findByUbicacionPadreIsNull();
        } 
        else ubicaciones = ubicacionRepository.findByUbicacionPadre_IdUbicacion(idPadre);

        log.debug("Se encontraron {} nodos hijos para el padre {}", ubicaciones.size(), idPadre != null ? idPadre : "RAIZ");

        return ubicaciones.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public UbicacionTecnicaDTO actualizarUbicacion(Long id, UbicacionTecnicaDTO dto) {
        log.info("Iniciando actualización de Ubicación Técnica ID: {}", id);

        UbicacionTecnica ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Fallo al actualizar: Ubicación ID {} no encontrada", id);
                    return new EntityNotFoundException("Ubicación no encontrada con ID: " + id);
                });

        if (dto.getIdPadre() != null && dto.getIdPadre().equals(id)) {
            log.error("Fallo de integridad: Intento de ciclo infinito. La ubicación ID {} intentó ser su propio padre.", id);
            throw new BusinessException("Una ubicación técnica no puede ser su propio padre.");
        }

        ubicacion.setNombre(dto.getNombre());
        ubicacion.setEstado(parsearEstado(dto.getEstado()));
        ubicacion.setTipo(dto.getTipo());
        
        UbicacionTecnica guardada = ubicacionRepository.save(ubicacion);
        log.info("Ubicación Técnica ID: {} actualizada exitosamente", id);
        return mapper.toDTO(guardada);
    }

    @Override
    @Transactional
    public void desactivarUbicacion(Long id) {
        log.info("Iniciando desactivación (baja logica) de Ubicación Técnica ID: {}", id);
        
        UbicacionTecnica ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Fallo al desactivar: Ubicación ID {} no encontrada", id);
                    return new EntityNotFoundException("Ubicación no encontrada con ID: " + id);
                });

        ubicacion.setEstado(EstadoOperativo.FueraDeServicio);
        ubicacionRepository.save(ubicacion);
        log.info("Ubicación Técnica ID: {} ha sido desactivada exitosamente", id);
    }
}