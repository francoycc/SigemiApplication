/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/SpringFramework/Service.java to edit this template
 */
package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.PlanMantenimientoDTO;
import com.sigemi.SigemiApplication.Entidades.Equipo;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Entidades.PlanMantenimiento;
import com.sigemi.SigemiApplication.Enums.EstadoOrden;
import com.sigemi.SigemiApplication.Enums.TipoMantenimiento;
import com.sigemi.SigemiApplication.Mapper.PlanMantenimientoMapper;
import com.sigemi.SigemiApplication.Repository.EquipoRepository;
import com.sigemi.SigemiApplication.Repository.OrdenMantenimientoRepository;
import com.sigemi.SigemiApplication.Repository.PlanMantenimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@EnableScheduling // habilita la ejecución de tareas programadas
public class PlanMantenimientoServiceImpl implements PlanMantenimientoService{

    @Autowired
    private PlanMantenimientoRepository planRepository;
    
    @Autowired
    private EquipoRepository equipoRepository; //
    
    @Autowired
    private OrdenMantenimientoRepository ordenRepository; //
    
    @Autowired
    private PlanMantenimientoMapper mapper;
            
    @Override
    @Transactional
    public PlanMantenimientoDTO crearPlan(PlanMantenimientoDTO dto) {
        // valido el equipo
        Equipo equipo = equipoRepository.findById(dto.getEquipoId())
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado con ID: " + dto.getEquipoId()));
        
        PlanMantenimiento plan = new PlanMantenimiento();
        plan.setNombre(dto.getNombre());
        plan.setFechaInicio(dto.getFechaInicio());
        plan.setDescripcion(dto.getDescripcion());
        plan.setFrecuenciaDias(dto.getFrecuenciaDias());
        plan.setEquipo(equipo);
        plan.setActivo(true); // Los planes se crean activos por defecto

        PlanMantenimiento guardado = planRepository.save(plan);
        return mapper.toDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public PlanMantenimientoDTO obtenerPorId(Long id) {
        PlanMantenimiento plan = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan no encontrado con ID: " + id));
        return mapper.toDTO(plan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanMantenimientoDTO> listarPlanes() {
        return planRepository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanMantenimientoDTO> listarPlanesPorEquipo(Long idEquipo) {
        return planRepository.findByEquipo_IdEquipo(idEquipo).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PlanMantenimientoDTO actualizarPlan(Long id, PlanMantenimientoDTO dto) {
        PlanMantenimiento plan = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan no encontrado con ID: " + id));

        Equipo equipo = equipoRepository.findById(dto.getEquipoId())
                .orElseThrow(() -> new EntityNotFoundException("Equipo no encontrado con ID: " + dto.getEquipoId()));

        plan.setNombre(dto.getNombre());
        plan.setDescripcion(dto.getDescripcion());
        plan.setFrecuenciaDias(dto.getFrecuenciaDias());
        plan.setFechaInicio(dto.getFechaInicio());
        plan.setActivo(dto.getActivo());
        plan.setEquipo(equipo);
        
        PlanMantenimiento guardado = planRepository.save(plan);
        return mapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public void desactivarPlan(Long id) {
        PlanMantenimiento plan = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan no encontrado con ID: " + id));
        plan.setActivo(false);
        planRepository.save(plan);
    }

    @Override
    @Transactional
    public void generarOrdenesAutomaticas() {
        System.out.println("--- INICIANDO TAREA PROGRAMADA: Generación de Órdenes Preventivas ---");
        List<PlanMantenimiento> planesActivos = planRepository.findByActivoTrue();
        LocalDate hoy = LocalDate.now();

        for (PlanMantenimiento plan : planesActivos) {
            // Usamos el método de la entidad para calcular la próxima ejecución
            LocalDate proximaEjecucion = plan.getProximaFechaEjecucion(); //

            // Si la fecha de ejecución es hoy o ya pasó
            if (!proximaEjecucion.isAfter(hoy)) {
                System.out.println("Generando orden para Plan ID: " + plan.getIdPlan() + " (Equipo: " + plan.getEquipo().getNombre() + ")");

                // 1. Crear la Orden de Mantenimiento
                OrdenMantenimiento orden = new OrdenMantenimiento();
                orden.setEquipo(plan.getEquipo());
                orden.setTipo(TipoMantenimiento.Preventivo); //
                orden.setDescripcion(plan.getDescripcion());
                orden.setPrioridad("Media"); // Prioridad por defecto para preventivos
                orden.setFechaCreacion(hoy);
                orden.setFechaInicio(hoy); // Se debe iniciar hoy
                orden.setFechaFin(hoy.plusDays(7)); // Dar 1 semana para completarla (configurable)
                orden.setEstado(EstadoOrden.Abierta); //
                
                // Generar un código único
                String codigo = String.format("WO-PREV-%d-%d", plan.getEquipo().getIdEquipo(), System.currentTimeMillis() % 10000);
                orden.setCodigoOrden(codigo);

                // Aquí deberías agregar Tareas (TareaMantenimiento) si el plan las detalla.
                // Por ahora, la descripción del plan va en la orden.

                ordenRepository.save(orden);

                // 2. ACTUALIZAR EL PLAN: Reiniciar el contador
                // Se establece la fecha de inicio a HOY, para que la próxima ejecución se calcule a partir de ahora.
                plan.setFechaInicio(hoy);
                planRepository.save(plan);
            }
        }
        System.out.println("--- TAREA PROGRAMADA FINALIZADA ---");
    }
    
}
