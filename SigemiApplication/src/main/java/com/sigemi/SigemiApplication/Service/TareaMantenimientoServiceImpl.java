
package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.TareaDTO;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import com.sigemi.SigemiApplication.Entidades.Usuario;
import com.sigemi.SigemiApplication.Enums.EstadoTarea;
import com.sigemi.SigemiApplication.Enums.RolUsuario;
import com.sigemi.SigemiApplication.Enums.TipoMantenimiento;
import com.sigemi.SigemiApplication.Excepciones.BusinessException;
import com.sigemi.SigemiApplication.Mapper.TareaMapper;
import com.sigemi.SigemiApplication.Repository.OrdenMantenimientoRepository;
import com.sigemi.SigemiApplication.Repository.TareaMantenimientoRepository;
import com.sigemi.SigemiApplication.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaMantenimientoServiceImpl implements TareaMantenimientoService {

    @Autowired
    private final TareaMantenimientoRepository tareaMantenimientoRepository;
    @Autowired
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private final OrdenMantenimientoRepository ordenRepository;
    @Autowired
    private final TareaMapper tareaMapper;
    
    public TareaMantenimientoServiceImpl(TareaMantenimientoRepository tareaMantenimientoRepo,
                            UsuarioRepository usuarioRepo,
                            OrdenMantenimientoRepository ordenRepo,
                            TareaMapper mapper){
        this.tareaMantenimientoRepository = tareaMantenimientoRepo;
        this.usuarioRepository = usuarioRepo;
        this.ordenRepository = ordenRepo;
        this.tareaMapper = mapper;
    }
    
//    @Override
//    public TareaMantenimiento crearTarea(TareaMantenimiento tarea) {
//        tarea.setEstado(EstadoTarea.Creada);
//        return tareaMantenimientoRepository.save(tarea);
//    }
//
//    @Override
//    public List<TareaMantenimiento> listarPorOrden(Long idOrden) {
//        return tareaMantenimientoRepository.findByOrden_IdOrden(idOrden);
//    }
//
//    @Override
//    public List<TareaMantenimiento> listarTareas() {
//        return tareaMantenimientoRepository.findAll();
//    }
//
//    @Override
//    public TareaMantenimiento obtenerPorId(Long id) {
//        return tareaMantenimientoRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
//    }
//        
//    
//    @Override
//    public TareaMantenimiento actualizarTarea(Long id, TareaMantenimiento nueva) {
//        TareaMantenimiento actual = obtenerPorId(id);
//        actual.setDescripcion(nueva.getDescripcion());
//        actual.setTipo(nueva.getTipo());
//        actual.setEstado(EstadoTarea.Creada);
//        actual.setTecnico(nueva.getTecnico());
//        actual.setTiempoInvertidoHoras(nueva.getTiempoInvertidoHoras());
//        actual.setEvidencias(nueva.getEvidencias());
//        actual.setFechaEjecucion(nueva.getFechaEjecucion());
//        return tareaMantenimientoRepository.save(actual);
//    }
//
//    @Override
//    public void pausarTarea(Long id) {
//        TareaMantenimiento tarea = obtenerPorId(id);
//        tarea.setEstado(EstadoTarea.Pausada);
//        tareaMantenimientoRepository.save(tarea);
//    }

    @Override
    public TareaDTO crearTarea(TareaDTO dto) {
        // validar usuario
        Usuario tecnico = usuarioRepository.findById(dto.getTecnicoId())
                .orElseThrow(() -> new EntityNotFoundException("No existe el tecnico para el ID ingresado:" + dto.getTecnicoId()));
        
        if (!RolUsuario.Operario.equals(tecnico.getRol().toString())) {
                throw new BusinessException("Usuario no es técnico: " + tecnico.getIdUsuario());
            }
        // validar orden
        OrdenMantenimiento orden = ordenRepository.findById(dto.getOrdenId())
                .orElseThrow(() -> new EntityNotFoundException("Orden de mantenimiento no encontrada"));
        
        TareaMantenimiento tarea = tareaMapper.toEntity(dto);
        tarea.setTecnico(tecnico);
        tarea.setOrden(orden);
        tarea.setTipo(TipoMantenimiento.valueOf(dto.getTipo()));
        tarea.setEstado(EstadoTarea.valueOf(dto.getEstado()));
        tarea.setFechaEjecucion(LocalDate.now());
        
        TareaMantenimiento guardada = tareaMantenimientoRepository.save(tarea);
        
        return tareaMapper.toDTO(guardada);
    }

    @Override
    public List<TareaDTO> listarPorOrden(Long idOrden) {
        List<TareaMantenimiento> tareasPorOrden = tareaMantenimientoRepository.findByOrden_IdOrden(idOrden);
        return tareasPorOrden.stream()
                .map(tarea -> tareaMapper.toDTO(tarea))
                .collect(Collectors.toList());
    }

    @Override
    public List<TareaDTO> listarTareas() {
        List<TareaMantenimiento> tareas = tareaMantenimientoRepository.findAll();
        return tareas.stream()
                .map(tarea -> tareaMapper.toDTO(tarea))
                .collect(Collectors.toList());
    }

    @Override
    public TareaDTO obtenerPorId(Long id) {
        TareaMantenimiento tarea = tareaMantenimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe la tarea para el ID ingresa:" + id));
        return tareaMapper.toDTO(tarea);
    }

    @Override
    public TareaDTO actualizarTarea(Long id, TareaDTO dto) {
        TareaMantenimiento tarea = tareaMantenimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        
        tarea.setDescripcion(dto.getDescripcion());
        tarea.setEstado(EstadoTarea.valueOf(dto.getEstado()));
        tarea.setTipo(TipoMantenimiento.valueOf(dto.getTipo()));
        tarea.setTiempoInvertidoHoras(dto.getTiempoInvertidoHoras());

        // validar tecnico si cambio
        if (dto.getTecnicoId() != null && !tarea.getTecnico().getIdUsuario().equals(dto.getTecnicoId())) {
            Usuario tecnico = usuarioRepository.findById(dto.getTecnicoId())
                    .orElseThrow(() -> new EntityNotFoundException("Técnico no encontrado"));
            tarea.setTecnico(tecnico);
        }

        return tareaMapper.toDTO(tareaMantenimientoRepository.save(tarea));
    }

    @Override
    public void pausarTarea(Long id) {
        TareaMantenimiento tarea = tareaMantenimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        tarea.setEstado(EstadoTarea.Pausada);
        tareaMantenimientoRepository.save(tarea);
    }
    
}
