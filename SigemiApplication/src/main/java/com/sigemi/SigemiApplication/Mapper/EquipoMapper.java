package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.EquipoDTO;
import com.sigemi.SigemiApplication.Entidades.Equipo;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface EquipoMapper {
    Equipo toEntity(EquipoDTO dto);

    EquipoDTO toDTO(Equipo entidad);
}
    