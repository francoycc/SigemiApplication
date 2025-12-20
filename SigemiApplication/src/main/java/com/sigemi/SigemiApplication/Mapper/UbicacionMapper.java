
package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.UbicacionTecnicaDTO;
import com.sigemi.SigemiApplication.Entidades.UbicacionTecnica;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UbicacionMapper {
    UbicacionTecnica toEntity(UbicacionTecnicaDTO dto);

    UbicacionTecnicaDTO toDTO(UbicacionTecnica entidad);
}
