package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.Entidades.UbicacionTecnica;
import com.sigemi.SigemiApplication.Enums.EstadoOperativo;
import com.sigemi.SigemiApplication.Repository.UbicacionTecnicaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UbicacionTecnicaServiceImpl implements UbicacionTecnicaService {

    private final UbicacionTecnicaRepository ubicacionRepository;
    @Override
    public UbicacionTecnica crearUbicacion(UbicacionTecnica ubicacion) {
        ubicacion.setEstado(EstadoOperativo.Operativo);
        return ubicacionRepository.save(ubicacion);
    }

    @Override
    public UbicacionTecnica modificarUbicacion(Long id, UbicacionTecnica ubicacion) {
        UbicacionTecnica actual = obtenerPorId(id);
        actual.setEstado(ubicacion.getEstado());
        actual.setNombre(ubicacion.getNombre());
        actual.setTipo(ubicacion.getTipo());
        actual.setEquipos(ubicacion.getEquipos());
        return ubicacionRepository.save(actual);
    }

    @Override
    public void desactivarUbicacion(Long id) {
        UbicacionTecnica ubicacion = obtenerPorId(id);
        ubicacion.setEstado(EstadoOperativo.FueraDeServicio);
        ubicacionRepository.save(ubicacion);
    }

    @Override
    public List<UbicacionTecnica> listarUbicaciones() {
        return ubicacionRepository.findAll();
    }

    @Override
    public UbicacionTecnica obtenerPorId(Long id) {
        return ubicacionRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Ubicacion no encontrada"));
    }

    @Override
    public List<UbicacionTecnica> listarUbicacionesPorPadre(Long idPadre) {
        UbicacionTecnica padre = obtenerPorId(idPadre);
        return padre.getSubUbicaciones();
    }
    
}
