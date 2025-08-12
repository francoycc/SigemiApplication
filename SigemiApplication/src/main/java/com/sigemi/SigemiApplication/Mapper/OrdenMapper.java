
package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.OrdenDTO;
import com.sigemi.SigemiApplication.DTO.TareaDTO;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface OrdenMapper {
    
    OrdenMantenimiento toEntity(OrdenDTO dto);

    OrdenDTO toDto(OrdenMantenimiento entidad);
    
    TareaDTO tareaToDto(TareaMantenimiento tarea);
}
