
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
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TareaMantenimientoServiceImpl implements TareaMantenimientoService {

    private final TareaMantenimientoRepository tareaMantenimientoRepository;
    private final UsuarioRepository usuarioRepository;
    private final OrdenMantenimientoRepository ordenRepository;
    private final TareaMapper tareaMapper;
    
    @Override
    @Transactional
    public TareaDTO crearTarea(TareaDTO dto) {
        log.info("Iniciando transacción para crear nueva Tarea de Mantenimiento");

        // 1. Validar usuario técnico
        Usuario tecnico = usuarioRepository.findById(dto.getTecnicoId())
                .orElseThrow(() -> new EntityNotFoundException("No existe el técnico para el ID ingresado: " + dto.getTecnicoId()));

        String rolTecnicoStr = tecnico.getRol() != null ? tecnico.getRol().toString() : "";
        if (!"OPERARIO".equalsIgnoreCase(rolTecnicoStr) && 
            !"ROLE_OPERARIO".equalsIgnoreCase(rolTecnicoStr) && 
            !"TECNICO".equalsIgnoreCase(rolTecnicoStr)) {
            throw new BusinessException("El usuario seleccionado no posee rol de técnico/operario: " + tecnico.getIdUsuario());
        }

        // 2. Validar orden
        OrdenMantenimiento orden = ordenRepository.findById(dto.getOrdenId())
                .orElseThrow(() -> new EntityNotFoundException("Orden de mantenimiento no encontrada con ID: " + dto.getOrdenId()));

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
        log.info("Listado de Tareas por Orden de mantenimiento con id: {} ", idOrden);
        List<TareaMantenimiento> tareasPorOrden = tareaMantenimientoRepository.findByOrden_IdOrden(idOrden);
        return tareasPorOrden.stream()
                .map(tarea -> tareaMapper.toDTO(tarea))
                .collect(Collectors.toList());
    }

    @Override
    public List<TareaDTO> listarTareas() {
        log.info("Listado de Tareas: ");
        List<TareaMantenimiento> tareas = tareaMantenimientoRepository.findAll();
        return tareas.stream()
                .map(tarea -> tareaMapper.toDTO(tarea))
                .collect(Collectors.toList());
    }

    @Override
    public TareaDTO obtenerPorId(Long id) {
        log.info("Tarea encontrada con id: {}", id);
        TareaMantenimiento tarea = tareaMantenimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe la tarea para el ID ingresa:" + id));
        return tareaMapper.toDTO(tarea);
    }

    @Override
    @Transactional
    public TareaDTO actualizarTarea(Long id, TareaDTO dto) {
        log.info("Iniciando actualización para la Tarea ID: {}", id);

        TareaMantenimiento tarea = tareaMantenimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No existe la tarea para el ID ingresado: " + id));

        if (dto.getDescripcion() != null) {
            tarea.setDescripcion(dto.getDescripcion());
        }
        if (dto.getTiempoInvertidoHoras() != null) {
            tarea.setTiempoInvertidoHoras(dto.getTiempoInvertidoHoras());
        }

        if (dto.getEstado() != null && !dto.getEstado().trim().isEmpty()) {
            try {
                tarea.setEstado(EstadoTarea.valueOf(dto.getEstado().toUpperCase().trim()));
            } catch (IllegalArgumentException e) {
                log.warn("Estado desconocido recibido: {}. Se conservará el estado actual.", dto.getEstado());
            }
        }

        if (dto.getTipo() != null && !dto.getTipo().trim().isEmpty()) {
            try {
                tarea.setTipo(TipoMantenimiento.valueOf(dto.getTipo().toUpperCase().trim()));
            } catch (IllegalArgumentException e) {
                log.warn("Tipo desconocido recibido: {}", dto.getTipo());
            }
        }

        // Guardar cambios
        TareaMantenimiento guardada = tareaMantenimientoRepository.save(tarea);
        return tareaMapper.toDTO(guardada);
    }

    @Override
    public void pausarTarea(Long id) {
        log.info("EstadoTarea con id {}: Pausada", id);
        TareaMantenimiento tarea = tareaMantenimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarea no encontrada"));
        tarea.setEstado(EstadoTarea.PAUSADA);
        tareaMantenimientoRepository.save(tarea);
    }

    @Override
    public List<TareaDTO> listarPorTecnico(Long idTecnico) {
        log.info("Listado de Tareas Asignadas al tecnico con id: {} ", idTecnico);
        List<TareaMantenimiento> tareasPorTecnico = tareaMantenimientoRepository.findByTecnico_IdUsuario(idTecnico);
        return tareasPorTecnico.stream()
                .map(tarea -> tareaMapper.toDTO(tarea))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<TareaDTO> obtenerTareasAsignadas() {
        // Obtenemos el usuario autenticado desde el token/sesión de Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Obtenemos los detalles del usuario para validar su rol
        Usuario usuario = usuarioRepository.findByNombreUsuario(username)
            .orElseThrow(() -> new BusinessException("Usuario no encontrado"));

        // Si es un operario/técnico, filtramos estrictamente por su ID en la base de datos
        if (usuario.getRol() == RolUsuario.OPERARIO) {
            List<TareaMantenimiento> tareas = tareaMantenimientoRepository.findByTecnico_IdUsuario(usuario.getIdUsuario());
            return tareas.stream().map(tareaMapper::toDTO).collect(Collectors.toList());
        }

        // Si es Supervisor o Administrador, ve todas las tareas para gestión general
        return tareaMantenimientoRepository.findAll().stream().map(tareaMapper::toDTO).collect(Collectors.toList());
    }
    
}
