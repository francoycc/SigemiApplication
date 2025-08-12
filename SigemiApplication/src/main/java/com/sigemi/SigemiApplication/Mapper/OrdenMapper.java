
package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.OrdenDTO;
import com.sigemi.SigemiApplication.DTO.TareaDTO;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import org.springframework.web.bind.annotation.Mapping;


@Mapper(componentModel = "spring")
public interface OrdenMapper {
    @Mapping(target = "tareas", ignore = true) // tareas las manejo en Service
    OrdenMantenimiento toEntity(OrdenDTO dto);

    OrdenDTO toDto(OrdenMantenimiento entidad);

    TareaDTO tareaToDto(TareaMantenimiento tarea);
}
