
package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.PlanMantenimientoDTO;
import com.sigemi.SigemiApplication.Entidades.PlanMantenimiento;
import org.mapstruct.Mapper;
import org.springframework.web.bind.annotation.Mapping;

@Mapper(componentModel = "spring")
public interface PlanMantenimientoMapper {
    
    PlanMantenimientoDTO toDTO(PlanMantenimiento entidad);
    
    PlanMantenimiento toEntity(PlanMantenimientoDTO dto);
}
