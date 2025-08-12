package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.OrdenDTO;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Entidades.Usuario;
import com.sigemi.SigemiApplication.Enums.EstadoOrden;
import com.sigemi.SigemiApplication.Mapper.OrdenMapper;
import com.sigemi.SigemiApplication.Repository.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdenMantenimientoServiceImpl implements OrdenMantenimientoService {

    private final OrdenMantenimientoRepository ordenRepository;
    private final EquipoRepository equipoRepository;
    private final TareaMantenimientoRepository tareaRepository
    private final UbicacionTecnicaRepository ubicacionRepository;
    private final UsuarioRepository usuarioRepository;
    private final OrdenMapper mapper;
    
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
        // validar supervisor
        Usuario supervisor = usuarioRepo.findById(dto.getSupervisorId())
            .orElseThrow(() -> new NotFoundException("Supervisor no encontrado"));

        if (!"SUPERVISOR".equalsIgnoreCase(supervisor.getRol())) {
            throw new BusinessException("Usuario no tiene rol de supervisor");
        }

        Equipo equipo = equipoRepo.findById(dto.getEquipoId())
            .orElseThrow(() -> new NotFoundException("Equipo no encontrado"));

        // crear orden
        OrdenMantenimiento orden = new OrdenMantenimiento();
        orden.setTipo(dto.getTipo());
        orden.setEquipo(equipo);
        orden.setSupervisor(supervisor);
        orden.setFechaCreacion(LocalDateTime.now());
        orden.setFechaPrevistaEjecucion(dto.getFechaPrevistaEjecucion());
        orden.setCreadoPor(usuarioQueCrea);
        orden.setCreadoEn(LocalDateTime.now());

        // crear tareas y asociar
        for (TareaCreateDTO tDto : dto.getTareas()) {
            Usuario tecnico = usuarioRepo.findById(tDto.getTecnicoId())
                .orElseThrow(() -> new NotFoundException("Técnico no encontrado: " + tDto.getTecnicoId()));

            if (!"TECNICO".equalsIgnoreCase(tecnico.getRol())) {
                throw new BusinessException("Usuario no es técnico: " + tecnico.getId());
            }

            TareaMantenimiento tarea = new TareaMantenimiento();
            tarea.setDescripcion(tDto.getDescripcion());
            tarea.setEstado("PENDIENTE");
            tarea.setTecnico(tecnico);
            tarea.setFechaCreacion(LocalDateTime.now());
            tarea.setCreadoPor(usuarioQueCrea);

            // agrega y setea la relación bidireccional
            orden.addTarea(tarea);
        }

        // persistir (cascade guardará tareas)
        OrdenMantenimiento ordenGuardada = ordenRepo.save(orden);

        // publicar evento para acciones asíncronas (notificaciones, sync SAP, calendar)
        eventPublisher.publishEvent(new OrdenCreadaEvent(this, ordenGuardada.getId()));

        // mapear respuesta
        OrdenResponseDTO resp = mapper.toDto(ordenGuardada);
        List<TareaResponseDTO> tareasDto = ordenGuardada.getTareas().stream()
            .map(mapper::tareaToDto)
            .collect(Collectors.toList());
        resp.setTareas(tareasDto);
        return resp;
    }
    
}
