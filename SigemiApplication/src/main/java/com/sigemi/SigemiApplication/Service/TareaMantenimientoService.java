package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import java.util.List;

public interface TareaMantenimientoService {
    TareaMantenimiento crearTarea(TareaMantenimiento tarea);
    
    List<TareaMantenimiento> listarPorOrden(Long idOrden);
    
    List<TareaMantenimiento> listarTareas();
    
    TareaMantenimiento obtenerPorId(Long id);
    
    TareaMantenimiento actualizarTarea(Long id, TareaMantenimiento tarea);
    
    void pausarTarea(Long id);
}
