package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.FallaReportadaDTO;
import com.sigemi.SigemiApplication.Entidades.Equipo;
import com.sigemi.SigemiApplication.Entidades.FallaReportada;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import com.sigemi.SigemiApplication.Entidades.Usuario;
import com.sigemi.SigemiApplication.Enums.Criticidad;
import com.sigemi.SigemiApplication.Enums.EstadoTarea;
import com.sigemi.SigemiApplication.Enums.TipoMantenimiento;
import com.sigemi.SigemiApplication.Excepciones.BusinessException;
import com.sigemi.SigemiApplication.Mapper.FallaMapper;
import com.sigemi.SigemiApplication.Repository.EquipoRepository;
import com.sigemi.SigemiApplication.Repository.FallaReportadaRepository;
import com.sigemi.SigemiApplication.Repository.OrdenMantenimientoRepository;
import com.sigemi.SigemiApplication.Repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FallaServiceImpl implements FallaService {

    @Autowired
    private final FallaReportadaRepository fallaRepository;
    @Autowired
    private final EquipoRepository equipoRepository;
    @Autowired
    private final UsuarioRepository usuarioRepository;
    @Autowired
    private final OrdenMantenimientoRepository ordenRepository;
    @Autowired
    private final FallaMapper mapper;

    

    
    @Override
    @Transactional
    public FallaReportadaDTO reportarFalla(FallaReportadaDTO dto) {
        // validar equipo
        Equipo equipo = equipoRepository.findById(dto.getEquipoId())
            .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado"));
        // validar usuario que reporta
        Usuario reportadoPor = usuarioRepository.findById(dto.getReportadoPorId())
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Guardar falla
        FallaReportada falla = new FallaReportada();
        falla.setEquipo(equipo);
        falla.setReportadoPor(reportadoPor);
        falla.setDescripcion(dto.getDescripcion());
        falla.setFechaReporte(LocalDate.now());
        falla.setCriticidad(Criticidad.valueOf(dto.getCriticidad()));
        fallaRepository.save(falla);

        // Crear Orden Correctiva
        OrdenMantenimiento orden = new OrdenMantenimiento();
        
        orden.setEquipo(equipo);
        orden.setTipo(TipoMantenimiento.Correctivo);
        orden.setSupervisor(reportadoPor); 
        orden.setFechaCreacion(LocalDate.now());
        //orden.setPrioridad(dto.getCriticidad()); 
        ordenRepository.save(orden);
        orden.setCodigoOrden("WO-CORR-" + equipo.getIdEquipo() + "-" + falla.getIdFalla());
        
        TareaMantenimiento tarea = new TareaMantenimiento();
        tarea.setDescripcion("Atender falla: " + falla.getDescripcion());
        tarea.setTipo(TipoMantenimiento.Correctivo);
        tarea.setEstado(EstadoTarea.Creada);
        tarea.setFechaEjecucion(LocalDate.now());

        if (dto.getTecnicoAsignadoId() != null) {
            Usuario tecnico = usuarioRepository.findById(dto.getTecnicoAsignadoId())
                    .orElseThrow(() -> new EntityNotFoundException("Técnico no encontrado"));
            tarea.setTecnico(tecnico);
        } else {
            // Podés definir un técnico “de guardia” por defecto
            throw new BusinessException("Debe asignarse un técnico responsable.");
        }

        orden.addTarea(tarea);
        ordenRepository.save(orden);
        
        
        FallaReportadaDTO resp = mapper.toDTO(falla);
        resp.setCodigoOrdenGenerada(orden.getCodigoOrden());
        return resp;
    }

    @Override
    public List<FallaReportadaDTO> listarFallas() {
        List<FallaReportada> fallas = fallaRepository.findAll();
        return fallas.stream()
            .map(falla -> mapper.toDTO(falla))
            .collect(Collectors.toList());
    }

    @Override
    public FallaReportadaDTO obtenerFallaPorId(Long id) {
        FallaReportada falla = fallaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Falla no encontrada"));
        return mapper.toDTO(falla);
    }

    @Override
    public List<FallaReportadaDTO> listarPorEquipo(Long idEquipo) {
        return fallaRepository.findByEquipo_IdEquipo(idEquipo).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
    
}
