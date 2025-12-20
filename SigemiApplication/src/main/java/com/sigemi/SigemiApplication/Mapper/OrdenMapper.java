
package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.OrdenDTO;
import com.sigemi.SigemiApplication.DTO.TareaDTO;
import com.sigemi.SigemiApplication.Entidades.OrdenMantenimiento;
import com.sigemi.SigemiApplication.Entidades.TareaMantenimiento;
import com.sigemi.SigemiApplication.Entidades.Usuario;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.web.bind.annotation.Mapping;


@Mapper(componentModel = "spring")
public interface OrdenMapper {
    
    @Mapping(source = "equipo.idEquipo", target = "equipoId")
    @Mapping(source = "equipo.nombre", target = "equipoNombre")
    @Mapping(target = "nombresTecnicos", ignore = true) 
    OrdenDTO toDTO(OrdenMantenimiento entidad);

    @Mapping(source = "equipoId", target = "equipo.idEquipo")
    OrdenMantenimiento toEntity(OrdenDTO dto);

    /**
     * Logica personalizada para extraer los tecnicos de las tareas
     * y ponerlos en el DTO de la Orden.
     */
    @AfterMapping
    default void calcularTecnicos(OrdenMantenimiento entidad, @MappingTarget OrdenDTO dto) {
        if (entidad.getTareas() == null || entidad.getTareas().isEmpty()) {
            return;
        }

        List<String> tecnicos = entidad.getTareas().stream()
                .map(TareaMantenimiento::getTecnico) // Obtener Usuario
                .filter(Objects::nonNull)            // Evitar NullPointerException
                .map(Usuario::getNombreUsuario)      // O getNombre() + " " + getApellido()
                .distinct()                          // Que no aparezca repetido
                .collect(Collectors.toList());

        dto.setNombresTecnicos(tecnicos);
    }
}
