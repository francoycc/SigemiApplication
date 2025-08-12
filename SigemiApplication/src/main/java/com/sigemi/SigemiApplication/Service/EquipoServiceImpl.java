package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.Entidades.Equipo;
import com.sigemi.SigemiApplication.Enums.EstadoOperativo;
import com.sigemi.SigemiApplication.Repository.EquipoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquipoServiceImpl implements EquipoService {

    @Autowired
    private final EquipoRepository equipoRepository;
    
    public EquipoServiceImpl(EquipoRepository equipoRepo){
        this.equipoRepository = equipoRepo;
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
    
}
