package Service;

import Entidades.OrdenMantenimiento;
import Enums.EstadoOrden;
import Repository.OrdenMantenimientoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdenMantenimientoServiceImpl implements OrdenMantenimientoService {

    private final OrdenMantenimientoRepository ordenRepository;
    @Override
    public OrdenMantenimiento crearOrdenMantenimiento(OrdenMantenimiento orden) {
        orden.setEstado(EstadoOrden.Abierta);
        orden.setFechaCreacion(java.time.LocalDate.now());
        return ordenRepository.save(orden);
    }

    @Override
    public List<OrdenMantenimiento> listarOrdenes() {
        return ordenRepository.findAll();
    }

    @Override
    public OrdenMantenimiento obtenerPorId(Long id) {
        return ordenRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Orden no encontrada"));
    }

    @Override
    public OrdenMantenimiento actualizarOrdenMantenimiento(Long id, OrdenMantenimiento nueva) {
        OrdenMantenimiento orden = obtenerPorId(id);
        orden.setDescripcion(nueva.getDescripcion());
        orden.setEstado(nueva.getEstado());
        orden.setFechaInicio(nueva.getFechaInicio());
        orden.setFechaFin(nueva.getFechaFin());
        orden.setTipo(nueva.getTipo());
        orden.setTecnicosAsignados(nueva.getTecnicosAsignados());
        return ordenRepository.save(nueva);
    }

    @Override
    public void finalizarOrdenMantenimiento(Long id) {
        OrdenMantenimiento orden = obtenerPorId(id);
        orden.setEstado(EstadoOrden.Finalizada);
        ordenRepository.save(orden);
    }
    
}
