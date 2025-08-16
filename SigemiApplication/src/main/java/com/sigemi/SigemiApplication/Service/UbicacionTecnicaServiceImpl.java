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
    
//    @Override
//    public UbicacionTecnica crearUbicacion(UbicacionTecnica ubicacion) {
//        ubicacion.setEstado(EstadoOperativo.Operativo);
//        return ubicacionRepository.save(ubicacion);
//    }
//
//    @Override
//    public UbicacionTecnica modificarUbicacion(Long id, UbicacionTecnica ubicacion) {
//        UbicacionTecnica actual = obtenerPorId(id);
//        actual.setEstado(ubicacion.getEstado());
//        actual.setNombre(ubicacion.getNombre());
//        actual.setTipo(ubicacion.getTipo());
//        actual.setEquipos(ubicacion.getEquipos());
//        return ubicacionRepository.save(actual);
//    }
//
//    @Override
//    public void desactivarUbicacion(Long id) {
//        UbicacionTecnica ubicacion = obtenerPorId(id);
//        ubicacion.setEstado(EstadoOperativo.FueraDeServicio);
//        ubicacionRepository.save(ubicacion);
//    }
//
//    @Override
//    public List<UbicacionTecnica> listarUbicaciones() {
//        return ubicacionRepository.findAll();
//    }
//
//    @Override
//    public UbicacionTecnica obtenerPorId(Long id) {
//        return ubicacionRepository.findById(id)
//                .orElseThrow(()-> new EntityNotFoundException("Ubicacion no encontrada"));
//    }
//
//    @Override
//    public List<UbicacionTecnica> listarUbicacionesPorPadre(Long idPadre) {
//        return ubicacionRepository.findByUbicacionPadre_IdUbicacion(idPadre);
//    }

    @Override
    @Transactional
    public UbicacionTecnicaDTO crearUbicacion(UbicacionTecnicaDTO dto) {
        // validar ubicacion
        if(ubicacionRepository.existsByCodigo(dto.getCodigo())){
            throw new BusinessException("Ya existe una ubicacion para el codigo ingresado.");
        }
        
        UbicacionTecnica ubicacionPadre = ubicacionRepository.findById(dto.getIdPadre())
            .orElseThrow(() -> new EntityNotFoundException("Ubicación técnica no encontrada"));
        
        UbicacionTecnica ubicacion = mapper.toEntity(dto);
        ubicacion.setEstado(EstadoOperativo.valueOf(dto.getEstado()));
        ubicacion.setUbicacionPadre(ubicacionPadre);
        //falta sububicaciones
        
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
        List<UbicacionTecnica> ubicaciones = ubicacionRepository.findByUbicacionPadre_IdUbicacion(idPadre);
        if (ubicaciones.isEmpty()) {
            System.out.println("No se encontraron equipos en la BD.");
        }
        List<UbicacionTecnicaDTO> ubicacionesDto = ubicaciones.stream()
            .map(ubicacion -> mapper.toDTO(ubicacion))
            .collect(Collectors.toList());
        return ubicacionesDto;
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
