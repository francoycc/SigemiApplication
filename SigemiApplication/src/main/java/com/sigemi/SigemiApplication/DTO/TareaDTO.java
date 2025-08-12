
package com.sigemi.SigemiApplication.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class TareaDTO {
    private Long id;
    
    @NotBlank
    private String descripcion;
    
    @NotBlank
    private String estado;
    
    @NotNull
    private Long tecnicoId;
    
    @NotBlank
    private String tecnicoNombre; 
}
