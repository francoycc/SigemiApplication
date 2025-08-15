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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipoServiceImpl implements EquipoService {
    private final EquipoRepository equipoRepository;
    private final UbicacionTecnicaRepository ubicacionRepository;
    private final EquipoMapper mapper;
    
    @Autowired
    public EquipoServiceImpl(EquipoRepository equipoRepo,
            UbicacionTecnicaRepository ubicacionRepo,
            EquipoMapper equipoMapper){
        this.equipoRepository = equipoRepo;
        this.ubicacionRepository = ubicacionRepo;
        this.mapper = equipoMapper;
    }
    
    @Override
    public Equipo crearEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

    @Override
    public List<Equipo> listarEquipos() {
        return equipoRepository.findAll();
    }

    @Override
    public Equipo obtenerPorId(Long id) {
        return equipoRepository.findById(id).
                orElseThrow(()-> new EntityNotFoundException("Equipo no encontrado"));
    }

    @Override
    public Equipo actualizarEquipo(Long id, Equipo nuevo) {
        Equipo actual = obtenerPorId(id);
        actual.setNombre(nuevo.getNombre());
        actual.setCriticidad(nuevo.getCriticidad());
        actual.setTipo(nuevo.getTipo());
        actual.setActivo(nuevo.getActivo());
        actual.setFrecuencia(nuevo.getFrecuencia());
        actual.setObservaciones(nuevo.getObservaciones());
        actual.setEstadoOperativo(nuevo.getEstadoOperativo());
        actual.setUbicacionTecnica(nuevo.getUbicacionTecnica());
        
        return equipoRepository.save(actual);
    }

    @Override
    public void deshabilitarEquipo(Long id) {
        Equipo equipo = obtenerPorId(id);
        equipo.setActivo(Boolean.FALSE);
        equipo.setEstadoOperativo(EstadoOperativo.FueraDeServicio);
        
        equipoRepository.save(equipo);
    }
    @Override
    public EquipoDTO crearEquipo(EquipoDTO dto){
        // validar equipo
        if(equipoRepository.existsByCodigoEquipo(dto.getCodigoEquipo())){
            throw new BusinessException("Ya existe un equipo para el codigo ingresado.");
        }
        if(dto.getNumeroSerie()!= null && equipoRepository.existsByNumeroSerie(dto.getNumeroSerie())){
            throw new BusinessException("Ya existe un equipo para el numero de serie ingresado.");
        }
        //traigo ubicacion
        UbicacionTecnica ubicacion = ubicacionRepository.findById(dto.getUbicacionTecnicaId())
                .orElseThrow(() -> new EntityNotFoundException("Ubicación técnica no encontrada"));
        
        Equipo equipo = mapper.toEntity(dto);
        equipo.setEstadoOperativo(EstadoOperativo.valueOf(dto.getEstadoOperativo()));
        equipo.setCriticidad(Criticidad.valueOf(dto.getCriticidad()));
        equipo.setUbicacionTecnica(ubicacion);
        equipo.setActivo(Boolean.TRUE);
        
        Equipo guardado = equipoRepository.save(equipo);
        return mapper.toDTO(guardado);
    }
}
