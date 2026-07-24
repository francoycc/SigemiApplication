
package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.UsoRepuestoDTO;
import com.sigemi.SigemiApplication.Entidades.UsoRepuesto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UsoRepuestoMapper {
    
    @Mapping(target = "orden", ignore = true)
    @Mapping(target = "repuesto", ignore = true)
    UsoRepuesto toEntity(UsoRepuestoDTO dto);
    
    @Mapping(source = "orden.idOrden", target = "ordenId")
    @Mapping(source = "repuesto.idRepuesto", target = "repuestoId")
    @Mapping(source = "repuesto.codigo", target = "repuestoCodigo")
    @Mapping(source = "repuesto.descripcion", target = "repuestoDescripcion")
    UsoRepuestoDTO toDTO(UsoRepuesto entidad);
}
