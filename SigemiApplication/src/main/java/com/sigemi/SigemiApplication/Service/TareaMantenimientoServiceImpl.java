
package com.sigemi.SigemiApplication.Service;

import org.springframework.stereotype.Service;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import com.sigemi.SigemiApplication.Enums.EstadoTarea;
import com.sigemi.SigemiApplication.Repository.TareaMantenimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TareaMantenimientoServiceImpl implements TareaMantenimientoService {

    private final TareaMantenimientoRepository tareaMantenimientoRepository;
    @Override
    public TareaMantenimiento crearTarea(TareaMantenimiento tarea) {
        tarea.setEstado(EstadoTarea.Creada);
        return tareaMantenimientoRepository.save(tarea);
    }

    @Override
    public List<TareaMantenimiento> listarPorOrden(Long idOrden) {
        return tareaMantenimientoRepository.findByOrden_IdOrden(idOrden);
    }

    @Override
    public List<TareaMantenimiento> listarTareas() {
        return tareaMantenimientoRepository.findAll();
    }

    @Override
    public TareaMantenimiento obtenerPorId(Long id) {
        return tareaMantenimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
    }
        
    
    @Override
    public TareaMantenimiento actualizarTarea(Long id, TareaMantenimiento nueva) {
        TareaMantenimiento actual = obtenerPorId(id);
        actual.setDescripcion(nueva.getDescripcion());
        actual.setTipo(nueva.getTipo());
        actual.setEstado(EstadoTarea.Creada);
        actual.setTecnico(nueva.getTecnico());
        actual.setTiempoInvertidoHoras(nueva.getTiempoInvertidoHoras());
        actual.setEvidencias(nueva.getEvidencias());
        actual.setFechaEjecucion(nueva.getFechaEjecucion());
        return tareaMantenimientoRepository.save(actual);
    }

    @Override
    public void pausarTarea(Long id) {
        TareaMantenimiento tarea = obtenerPorId(id);
        tarea.setEstado(EstadoTarea.Pausada);
        tareaMantenimientoRepository.save(tarea);
    }
    
}
