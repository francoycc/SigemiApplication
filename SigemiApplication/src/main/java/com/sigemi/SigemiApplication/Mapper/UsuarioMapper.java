package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.UsuarioDTO;
import com.sigemi.SigemiApplication.Entidades.Usuario;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toEntity(UsuarioDTO dto);
    
    UsuarioDTO toDTO(Usuario entidad);
}
