
package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.PlanMantenimientoDTO;
import com.sigemi.SigemiApplication.Entidades.PlanMantenimiento;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PlanMantenimientoMapper {
    
    @Mapping(source = "equipo.idEquipo", target = "equipoId")
    @Mapping(source = "equipo.nombre", target = "equipoNombre")
    PlanMantenimientoDTO toDTO(PlanMantenimiento entidad);
    
    @Mapping(target = "equipo", ignore = true)
    PlanMantenimiento toEntity(PlanMantenimientoDTO dto);
}
