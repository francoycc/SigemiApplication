package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.OrdenDTO;
import com.sigemi.SigemiApplication.DTO.TareaDTO;
import com.sigemi.SigemiApplication.Entidades.Equipo;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import com.sigemi.SigemiApplication.Entidades.Usuario;
import com.sigemi.SigemiApplication.Enums.EstadoOrden;
import com.sigemi.SigemiApplication.Enums.EstadoTarea;
import com.sigemi.SigemiApplication.Enums.RolUsuario;
import com.sigemi.SigemiApplication.Enums.TipoMantenimiento;
import com.sigemi.SigemiApplication.Excepciones.*;
import com.sigemi.SigemiApplication.Mapper.OrdenMapper;
import com.sigemi.SigemiApplication.Mapper.TareaMapper;
import com.sigemi.SigemiApplication.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OrdenMantenimientoServiceImpl implements OrdenMantenimientoService {

    @Autowired
    private OrdenMantenimientoRepository ordenRepository;
    @Autowired
    private EquipoRepository equipoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private OrdenMapper mapper;
    @Autowired
    private TareaMapper mapperTarea;

    @Override
    @Transactional
    public OrdenDTO crearOrden(OrdenDTO dto) {

        // 1. Validar supervisor
        Usuario supervisor = usuarioRepository.findById(dto.getIdSupervisor())
            .orElseThrow(() -> new EntityNotFoundException("Supervisor no encontrado con ID: " + dto.getIdSupervisor()));

        String rolSupStr = supervisor.getRol() != null ? supervisor.getRol().toString() : "";
        if (!"SUPERVISOR".equalsIgnoreCase(rolSupStr) && !"ROLE_SUPERVISOR".equalsIgnoreCase(rolSupStr)) {
            throw new BusinessException("El usuario seleccionado no posee rol de SUPERVISOR (ID: " + supervisor.getIdUsuario() + ")");
        }

        // 2. Validar equipo
        Equipo equipo = equipoRepository.findById(dto.getIdEquipo())
            .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado con ID: " + dto.getIdEquipo()));

        // 3. Crear orden
        OrdenMantenimiento orden = new OrdenMantenimiento();
        orden.setTipo(TipoMantenimiento.valueOf(dto.getTipo().toUpperCase()));
        orden.setEquipo(equipo);
        orden.setSupervisor(supervisor);
        orden.setFechaCreacion(LocalDate.now());
        orden.setFechaFin(dto.getFechaPrevistaEjecucion());
        orden.setPrioridad(dto.getPrioridad());
        orden.setDescripcion(dto.getDescripcion());
        orden.setEstado(EstadoOrden.valueOf(dto.getEstadoOrden().toUpperCase()));

        // 4. Crear tareas y asociar
        for (TareaDTO tareaDto : dto.getTareas()) {
            Usuario tecnico = usuarioRepository.findById(tareaDto.getTecnicoId())
                .orElseThrow(() -> new EntityNotFoundException("Técnico no encontrado con ID: " + tareaDto.getTecnicoId()));

            String rolTecStr = tecnico.getRol() != null ? tecnico.getRol().toString() : "";
            if (!"OPERARIO".equalsIgnoreCase(rolTecStr) && !"ROLE_OPERARIO".equalsIgnoreCase(rolTecStr) && !"TECNICO".equalsIgnoreCase(rolTecStr)) {
                throw new BusinessException("El usuario asignado a la tarea no es técnico/operario (ID: " + tecnico.getIdUsuario() + ")");
            }

            TareaMantenimiento tarea = new TareaMantenimiento();
            tarea.setDescripcion(tareaDto.getDescripcion());
            tarea.setEstado(EstadoTarea.valueOf(tareaDto.getEstado()));
            tarea.setTecnico(tecnico);
            tarea.setFechaEjecucion(LocalDate.now());
            tarea.setTipo(TipoMantenimiento.valueOf(orden.getTipo().toString().toUpperCase())); 

            // Asocia bidireccionalmente la tarea a la orden
            orden.addTarea(tarea);
        }

        // Persistir orden con tareas en cascada
        OrdenMantenimiento ordenGuardada = ordenRepository.save(orden);

        String codigo = "WO-" + String.format("%03d", equipo.getIdEquipo())
                    + "-" + String.format("%05d", ordenGuardada.getIdOrden());
        ordenGuardada.setCodigoOrden(codigo);

        ordenGuardada = ordenRepository.save(ordenGuardada);

        // Mapear respuesta
        OrdenDTO resp = mapper.toDto(ordenGuardada);
        List<TareaDTO> tareasDto = ordenGuardada.getTareas().stream()
            .map(mapperTarea::toDTO)
            .collect(Collectors.toList());
        resp.setTareas(tareasDto);

        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public OrdenDTO obtenerPorId(Long id) {
        OrdenMantenimiento orden = ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró la orden para el ID: " + id));
        return mapper.toDto(orden);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenDTO> listarOrdenes() {
        List<OrdenDTO> ordenesDto = ordenRepository.findAll().stream()
            .map(orden -> mapper.toDto(orden))
            .collect(Collectors.toList());
        if (ordenesDto.isEmpty()) {
            System.out.println("No se encontraron ordenes en la BD.");
        }

        System.out.println("Se encontraron ordenes en la base de datos: " + ordenesDto.size());

        return ordenesDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrdenDTO> listarPorEquipo(Long idEquipo, Pageable pageable) {
        
        return ordenRepository.findByEquipo_IdEquipo(idEquipo, pageable)
                .map(mapper::toDto);
    }

    @Override
    public OrdenDTO actualizarOrden(Long id, OrdenDTO dto) {
        OrdenMantenimiento orden = ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada"));

        // solo actualizamos ciertos campos
        orden.setDescripcion(dto.getDescripcion());
        if (dto.getEstadoOrden() != null) {
            orden.setEstado(EstadoOrden.valueOf(dto.getEstadoOrden()));
        }
        orden.setFechaFin(dto.getFechaPrevistaEjecucion());
        orden.setPrioridad(dto.getPrioridad());
        if (dto.getTipo() != null) {
            orden.setTipo(TipoMantenimiento.valueOf(dto.getTipo()));
        }
        OrdenMantenimiento actualizada = ordenRepository.save(orden);
        
        return mapper.toDto(actualizada);
    }

    @Override
    public OrdenDTO finalizarOrden(Long id) {
        OrdenMantenimiento orden = ordenRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Orden no encontrada con el ID: " + id));

        orden.setEstado(EstadoOrden.COMPLETADA);
        orden.setFechaFin(LocalDate.now());

        OrdenMantenimiento finalizada = ordenRepository.save(orden);
        return mapper.toDto(finalizada);
    }
    
}
