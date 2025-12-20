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
    public OrdenDTO crearOrden(OrdenDTO dto, String usuarioCreador) {
        
        // Validar supervisor
        Usuario supervisor = usuarioRepository.findById(dto.getSupervisorId())
            .orElseThrow(() -> new EntityNotFoundException("Supervisor no encontrado con ID: " + dto.getSupervisorId()));

        if (!RolUsuario.Supervisor.equals(supervisor.getRol().toString())) {
            throw new BusinessException("Usuario no tiene rol de supervisor");
        }

        // validar equipo
        Equipo equipo = equipoRepository.findById(dto.getEquipoId())
            .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado con ID: " + dto.getEquipoId()));

        // Crear oden
        OrdenMantenimiento orden = new OrdenMantenimiento();
        
        orden.setTipo(TipoMantenimiento.valueOf(dto.getTipo()));
        orden.setEquipo(equipo);
        orden.setSupervisor(supervisor);
        orden.setFechaCreacion(LocalDate.now());
        orden.setFechaFin(dto.getFechaPrevistaEjecucion());
        orden.setPrioridad(dto.getPrioridad());
        orden.setDescripcion(dto.getDescripcion());
        orden.setEstado(EstadoOrden.valueOf(dto.getEstadoOrden()));


        // crear tareas y asociar
        for (TareaDTO tareaDto : dto.getTareas()) {
            Usuario tecnico = usuarioRepository.findById(tareaDto.getTecnicoId())
                    .orElseThrow(()-> new EntityNotFoundException("Tecnico no encontrado con ID: "+ tareaDto.getTecnicoId()));

            if (!RolUsuario.Operario.equals(tecnico.getRol().toString())) {
                throw new BusinessException("Usuario no es técnico: " + tecnico.getIdUsuario());
            }

            TareaMantenimiento tarea = new TareaMantenimiento();
            tarea.setDescripcion(tareaDto.getDescripcion());
            tarea.setEstado(EstadoTarea.valueOf(tareaDto.getEstado()));
            tarea.setTecnico(tecnico);
            tarea.setFechaEjecucion(LocalDate.now());
            tarea.setTipo(orden.getTipo()); 
            // agrega y setea la relación 
            orden.addTarea(tarea);
        }

        // persistir (con tareas)
        OrdenMantenimiento ordenGuardada = ordenRepository.save(orden);
        String codigo = "WO-" + String.format("%03d", equipo.getIdEquipo())
                    + "-" + String.format("%05d", ordenGuardada.getIdOrden());
        orden.setCodigoOrden(codigo);
        
        ordenGuardada = ordenRepository.save(ordenGuardada);
        // publicar evento para acciones asincronas 
        //eventPublisher.publishEvent(new OrdenCreadaEvent(this, ordenGuardada.getId()));

        // mapear respuesta
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

        orden.setEstado(EstadoOrden.Finalizada);
        orden.setFechaFin(LocalDate.now());

        OrdenMantenimiento finalizada = ordenRepository.save(orden);
        return mapper.toDto(finalizada);
    }
    
}
