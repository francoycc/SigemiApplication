
package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.FallaReportadaDTO;
import com.sigemi.SigemiApplication.Entidades.FallaReportada;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FallaMapper {
    
    FallaReportada toEntity(FallaReportadaDTO dto);
    
    FallaReportadaDTO toDTO(FallaReportada entidad);
}
