package Repository;

import Entidades.UbicacionTecnica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UbicacionTecnicaRepository extends JpaRepository<UbicacionTecnica, Long> {
    Boolean existsByCodigo(String codigo);
    List<UbicacionTecnica> findByUbicacionTecnicaPadre(Long idUbicacionTecnicaPadre);
}
