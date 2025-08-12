package com.sigemi.SigemiApplication.DTO;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class OrdenDTO {
    private Long id;
    @NotNull
    private String tipo;

    @NotNull
    private Long equipoId;

    @NotNull
    private Long supervisorId;

    @NotNull
    private LocalDate fechaCreacion;
    
    @NotNull
    private LocalDate fechaPrevistaEjecucion;

    @NotEmpty
    private List<TareaDTO> tareas;
}
