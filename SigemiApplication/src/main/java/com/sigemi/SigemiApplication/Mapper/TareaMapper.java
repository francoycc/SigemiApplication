package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.TareaDTO;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TareaMapper {
    
    @Mapping(target = "tecnico", ignore = true)
    @Mapping(target = "orden", ignore = true)
    @Mapping(target = "evidencias", ignore = true)
    TareaMantenimiento toEntity(TareaDTO dto);
    
    @Mapping(source = "tecnico.idUsuario", target = "tecnicoId")
    @Mapping(source = "tecnico.nombreUsuario", target = "tecnicoNombre") 
    @Mapping(source = "orden.idOrden", target = "ordenId")
    TareaDTO toDTO(TareaMantenimiento entidad);
}
