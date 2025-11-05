package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.TareaDTO;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import java.util.List;

public interface TareaMantenimientoService {
//    TareaMantenimiento crearTarea(TareaMantenimiento tarea);
//    List<TareaMantenimiento> listarPorOrden(Long idOrden);
//    List<TareaMantenimiento> listarTareas();
//    TareaMantenimiento obtenerPorId(Long id);
//    TareaMantenimiento actualizarTarea(Long id, TareaMantenimiento tarea);
//    void pausarTarea(Long id);
    TareaDTO crearTarea(TareaDTO dto);
    List<TareaDTO> listarPorOrden(Long idOrden);
    List<TareaDTO> listarTareas();
    TareaDTO obtenerPorId(Long id);
    TareaDTO actualizarTarea(Long id, TareaDTO dto);
    void pausarTarea(Long id); 
    
    // las tareas asignadas a un tecnico
    List<TareaDTO> listarPorTecnico(Long idTecnico);
}
