package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.UbicacionTecnicaDTO;
import com.sigemi.SigemiApplication.Entidades.UbicacionTecnica;
import java.util.List;

public interface UbicacionTecnicaService {
//    UbicacionTecnica crearUbicacion(UbicacionTecnica ubicacion);
//    UbicacionTecnica modificarUbicacion(Long id, UbicacionTecnica ubicacion);
//    void desactivarUbicacion(Long id);
//    List<UbicacionTecnica> listarUbicaciones();
//    UbicacionTecnica obtenerPorId(Long id);
//    List<UbicacionTecnica> listarUbicacionesPorPadre(Long idPadre);
    UbicacionTecnicaDTO crearUbicacion(UbicacionTecnicaDTO dto);
    UbicacionTecnicaDTO obtenerPorId(Long id);
    List<UbicacionTecnicaDTO> listarUbicaciones();
    List<UbicacionTecnicaDTO> listarUbicacionesPorPadre(Long idPadre);
}
