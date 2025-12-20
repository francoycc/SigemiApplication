package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.EquipoDTO;
import com.sigemi.SigemiApplication.Entidades.Equipo;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface EquipoMapper {
    
    @Mapping(target = "ubicacionTecnica", ignore = true) 
    @Mapping(target = "planesMantenimiento", ignore = true)
    Equipo toEntity(EquipoDTO dto);

    @Mapping(source = "ubicacionTecnica.idUbicacion", target = "ubicacionTecnicaId")
    EquipoDTO toDTO(Equipo entidad);
}
    