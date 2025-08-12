package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.OrdenDTO;
import com.sigemi.SigemiApplication.DTO.TareaDTO;
import com.sigemi.SigemiApplication.Entidades.Equipo;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import com.sigemi.SigemiApplication.Entidades.Usuario;
import com.sigemi.SigemiApplication.Enums.EstadoOrden;
import com.sigemi.SigemiApplication.Enums.EstadoTarea;
import com.sigemi.SigemiApplication.Enums.TipoMantenimiento;
import com.sigemi.SigemiApplication.Excepciones.*;
import com.sigemi.SigemiApplication.Mapper.OrdenMapper;
import com.sigemi.SigemiApplication.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdenMantenimientoServiceImpl implements OrdenMantenimientoService {

    private final OrdenMantenimientoRepository ordenRepository;
    private final EquipoRepository equipoRepository;
    private final TareaMantenimientoRepository tareaRepository;
    //private final UbicacionTecnicaRepository ubicacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final OrdenMapper mapper;
    private final ApplicationEventPublisher eventPublisher;
    
    @Autowired
    public OrdenMantenimientoServiceImpl(OrdenMantenimientoRepository ordenRepo,
                            TareaMantenimientoRepository tareaRepo,
                            UsuarioRepository usuarioRepo,
                            EquipoRepository equipoRepo,
                            OrdenMapper mapper,
                            ApplicationEventPublisher eventPublisher) {
        this.ordenRepository = ordenRepo;
        this.tareaRepository = tareaRepo;
        this.usuarioRepository = usuarioRepo;
        this.equipoRepository = equipoRepo;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }
    
    @Override
    public OrdenMantenimiento crearOrdenMantenimiento(OrdenMantenimiento orden) {
        orden.setEstado(EstadoOrden.Abierta);
        orden.setFechaCreacion(java.time.LocalDate.now());
        return ordenRepository.save(orden);
    }

    @Override
    public List<OrdenMantenimiento> listarOrdenes() {
        return ordenRepository.findAll();
    }

    @Override
    public OrdenMantenimiento obtenerPorId(Long id) {
        return ordenRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Orden no encontrada"));
    }

    @Override
    public OrdenMantenimiento actualizarOrdenMantenimiento(Long id, OrdenMantenimiento nueva) {
        OrdenMantenimiento orden = obtenerPorId(id);
        orden.setDescripcion(nueva.getDescripcion());
        orden.setEstado(nueva.getEstado());
        orden.setFechaInicio(nueva.getFechaInicio());
        orden.setFechaFin(nueva.getFechaFin());
        orden.setTipo(nueva.getTipo());
        orden.setTecnicosAsignados(nueva.getTecnicosAsignados());
        return ordenRepository.save(nueva);
    }

    @Override
    public void finalizarOrdenMantenimiento(Long id) {
        OrdenMantenimiento orden = obtenerPorId(id);
        orden.setEstado(EstadoOrden.Finalizada);
        ordenRepository.save(orden);
    }

    @Override
    @Transactional
    public OrdenDTO crearOrden(OrdenDTO dto, String usuarioCreador) {
        
        // Validar supervisor
        Usuario supervisor = usuarioRepository.findById(dto.getSupervisorId())
            .orElseThrow(() -> new EntityNotFoundException("Supervisor no encontrado"));

        if (!"SUPERVISOR".equalsIgnoreCase(supervisor.getRol().toString())) {
            try {
                throw new BusinessException("Usuario no tiene rol de supervisor");
            } catch (BusinessException ex) {
                Logger.getLogger(OrdenMantenimientoServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        Equipo equipo = equipoRepository.findById(dto.getEquipoId())
            .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));

        // Crear oden
        OrdenMantenimiento orden = new OrdenMantenimiento();
        
        orden.setTipo(TipoMantenimiento.valueOf(dto.getTipo()));
        orden.setEquipo(equipo);
        orden.setSupervisor(supervisor);
        orden.setFechaCreacion(LocalDate.now());
        orden.setFechaFin(dto.getFechaPrevistaEjecucion());
        orden.setPrioridad(dto.getPrioridad());

        // crear tareas y asociar
        for (TareaDTO tareaDto : dto.getTareas()) {
            Usuario tecnico = usuarioRepository.findById(tareaDto.getTecnicoId())
                    .orElseThrow(()-> new EntityNotFoundException("Tecnico no encontrado"+ tareaDto.getTecnicoId()));

            if (!"TECNICO".equalsIgnoreCase(tecnico.getRol().toString())) {
                try {
                    throw new BusinessException("Usuario no es técnico: " + tecnico.getIdUsuario());
                } catch (BusinessException ex) {
                    Logger.getLogger(OrdenMantenimientoServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            TareaMantenimiento tarea = new TareaMantenimiento();
            tarea.setDescripcion(tareaDto.getDescripcion());
            tarea.setEstado(EstadoTarea.valueOf(tareaDto.getEstado()));
            tarea.setTecnico(tecnico);
            tarea.setFechaEjecucion(LocalDate.now());

            // agrega y setea la relación bidireccional
            orden.addTarea(tarea);
        }

        // persistir (cascade guardara tareas)
        OrdenMantenimiento ordenGuardada = ordenRepository.save(orden);

        // publicar evento para acciones asincronas 
        //eventPublisher.publishEvent(new OrdenCreadaEvent(this, ordenGuardada.getId()));

        // mapear respuesta
        OrdenDTO resp = mapper.toDto(ordenGuardada);
        List<TareaDTO> tareasDto = ordenGuardada.getTareas().stream()
            .map(mapper::tareaToDto)
            .collect(Collectors.toList());
        resp.setTareas(tareasDto);
        return resp;
    }
    
}
