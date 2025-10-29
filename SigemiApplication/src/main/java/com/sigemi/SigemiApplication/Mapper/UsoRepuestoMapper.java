
package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.UsoRepuestoDTO;
import com.sigemi.SigemiApplication.Entidades.UsoRepuesto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsoRepuestoMapper {
    UsoRepuesto toEntity(UsoRepuestoDTO dto);
    UsoRepuestoDTO toDTO(UsoRepuesto entidad);
}
