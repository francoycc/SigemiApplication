
package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.FallaReportadaDTO;
import com.sigemi.SigemiApplication.Entidades.FallaReportada;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FallaMapper {
    
    @Mapping(target = "equipo", ignore = true)
    @Mapping(target = "reportadoPor", ignore = true)
    FallaReportada toEntity(FallaReportadaDTO dto);
    
    @Mapping(source = "equipo.idEquipo", target = "equipoId")
    @Mapping(source = "equipo.nombre", target = "nombreEquipo")
    @Mapping(source = "reportadoPor.idUsuario", target = "reportadoPorId")
    @Mapping(source = "reportadoPor.nombreUsuario", target = "nombreUsuarioReporta")
    // codigoOrdenGenerada se setea manualmente en el servicio si aplica
    @Mapping(target = "codigoOrdenGenerada", ignore = true) 
    // tecnicoAsignadoId es parte de la orden generada, no de la entidad falla 
    @Mapping(target = "tecnicoAsignadoId", ignore = true)
    FallaReportadaDTO toDTO(FallaReportada entidad);
}
