/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sigemi.SigemiApplication.Mapper;

import com.sigemi.SigemiApplication.DTO.RepuestoDTO;
import com.sigemi.SigemiApplication.Entidades.Repuesto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RepuestoMapper {
    Repuesto toEntity(RepuestoDTO dto);
    RepuestoDTO toDTO(Repuesto entidad);
}
