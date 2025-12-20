
package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.OrdenDTO;
import com.sigemi.SigemiApplication.DTO.TareaDTO;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import com.sigemi.SigemiApplication.Entidades.Usuario;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface OrdenMapper {
    
    @Mapping(source = "equipo.idEquipo", target = "equipoId")
    @Mapping(source = "equipo.nombre", target = "equipoNombre")
    @Mapping(target = "nombresTecnicos", ignore = true)
    OrdenDTO toDto(OrdenMantenimiento entidad);

    @Mapping(source = "equipoId", target = "equipo.idEquipo")
    OrdenMantenimiento toEntity(OrdenDTO dto);
    
    TareaDTO tareaToDto(TareaMantenimiento tarea);
    
    @AfterMapping
    default void calcularTecnicos(OrdenMantenimiento entidad, @MappingTarget OrdenDTO dto) {
        if (entidad.getTareas() == null || entidad.getTareas().isEmpty()) {
            return;
        }

        List<String> tecnicos = entidad.getTareas().stream()
                .map(TareaMantenimiento::getTecnico)
                .filter(Objects::nonNull)
                .map(usuario -> usuario.getNombre() + " " + usuario.getApellido()) // O getNombreUsuario()
                .distinct()
                .collect(Collectors.toList());

        dto.setNombresTecnicos(tecnicos);
    }
}
