package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.TareaDTO;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TareaMapper {
    TareaMantenimiento toEntity(TareaDTO dto);
    
    TareaDTO toDTO(TareaMantenimiento entidad);
}
