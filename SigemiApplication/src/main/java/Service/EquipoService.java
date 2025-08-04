package Service;

import Entidades.Equipo;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public interface EquipoService {
    Equipo crearEquipo(Equipo equipo);
    List<Equipo> listarEquipos();
    Equipo obtenerPorId(Long id);
    Equipo actualizarEquipo(Long id, Equipo equipo);
    void deshabilitarEquipo(Long id);
}
