package Service;

import Entidades.UbicacionTecnica;
import java.util.List;

public interface UbicacionTecnicaService {
    UbicacionTecnica crearUbicacion(UbicacionTecnica ubicacion);
    UbicacionTecnica modificarUbicacion(Long id, UbicacionTecnica ubicacion);
    void desactivarUbicacion(Long id);
    List<UbicacionTecnica> listarUbicaciones();
    UbicacionTecnica obtenerPorId(Long id);
    List<UbicacionTecnica> listarUbicacionesPorPadre(Long idPadre);
}
