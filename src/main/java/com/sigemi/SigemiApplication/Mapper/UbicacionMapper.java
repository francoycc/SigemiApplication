
package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.UbicacionTecnicaDTO;
import com.sigemi.SigemiApplication.Entidades.UbicacionTecnica;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UbicacionMapper {
    
    @Mapping(target = "ubicacionPadre", ignore = true)
    @Mapping(target = "subUbicaciones", ignore = true)
    @Mapping(target = "equipos", ignore = true)
    UbicacionTecnica toEntity(UbicacionTecnicaDTO dto);

    @Mapping(source = "ubicacionPadre.idUbicacion", target = "idPadre")
    @Mapping(target = "sububicaciones", ignore = true)
    UbicacionTecnicaDTO toDTO(UbicacionTecnica entidad);
}
