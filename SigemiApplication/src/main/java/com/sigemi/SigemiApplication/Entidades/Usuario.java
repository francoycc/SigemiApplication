
package com.sigemi.SigemiApplication.Entidades;

import com.sigemi.SigemiApplication.Enums.RolUsuario;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @Column(nullable = false, unique = true)
    private String nombreUsuario;
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String apellido;
    
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolUsuario rol;
    
    @Column(nullable = false)
    private Long telefono;
    
    private Boolean activo;
    
    @OneToMany(mappedBy = "usuarioAsignado")
    private List<TareaMantenimiento> tareas;
}
