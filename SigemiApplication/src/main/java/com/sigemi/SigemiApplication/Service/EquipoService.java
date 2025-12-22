package com.sigemi.SigemiApplication.Service;

import com.sigemi.SigemiApplication.DTO.EquipoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public interface EquipoService {
    EquipoDTO crearEquipo(EquipoDTO dto);
    EquipoDTO buscarPorId(Long id);
    Page<EquipoDTO> listarEquipos(Pageable pageable);
    EquipoDTO actualizarEquipo(Long id, EquipoDTO dto);
    void desactivarEquipo(Long id);
}
