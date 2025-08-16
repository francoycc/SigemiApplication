package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.EquipoDTO;
import com.sigemi.SigemiApplication.Entidades.Equipo;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public interface EquipoService {
//    Equipo crearEquipo(Equipo equipo);
//    List<Equipo> listarEquipos();
//    Equipo obtenerPorId(Long id);
//    Equipo actualizarEquipo(Long id, Equipo equipo);
//    void deshabilitarEquipo(Long id);
    EquipoDTO crearEquipo(EquipoDTO dto);
    EquipoDTO buscarPorId(Long id);
    List<EquipoDTO> listarEquipos();
    EquipoDTO actualizarEquipo(Long id, EquipoDTO dto);
    void desactivarEquipo(Long id);
}
