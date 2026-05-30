package com.sigemi.SigemiApplication.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desactivar CSRF: Es necesario para que funcionen las peticiones POST/PUT desde React
            // sin necesidad de tokens de sesión (cookies).
            .csrf(csrf -> csrf.disable())
            
            // 2. Configurar CORS: Vincula la configuración de abajo para aceptar peticiones desde localhost:3000
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 3. Gestión de Permisos de URL (Aquí solucionamos el 403)
            .authorizeHttpRequests(auth -> auth
                // Permitir acceso libre al Login
                .requestMatchers("/api/auth/**").permitAll()
                
                // Módulo de Equipos y Ubicaciones (Solo Supervisores y Administradores)
                .requestMatchers(HttpMethod.POST, "/api/equipos/**", "/api/ubicaciones/**").hasAnyRole("SUPERVISOR", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/equipos/**", "/api/ubicaciones/**").hasAnyRole("SUPERVISOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/equipos/**", "/api/ubicaciones/**").hasAnyRole("SUPERVISOR", "ADMIN")

                // Órdenes de Mantenimiento (Creación y asignación exclusiva de Supervisores)
                .requestMatchers("/api/ordenes/nueva", "/api/ordenes/editar/**").hasRole("SUPERVISOR")

                // Tareas y ejecución (Permitido para Operarios/Técnicos y Supervisores)
                .requestMatchers("/api/tareas/**").hasAnyRole("OPERARIO", "SUPERVISOR", "ADMIN")

                // Auditoría y Logs (Exclusivo Administradores)
                .requestMatchers("/api/logs/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            );

        return http.build();
    }

    // Configuración detallada de CORS (Intercambio de recursos de origen cruzado)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // A. Origen Permitido: Tu Frontend
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        
        // B. Métodos HTTP Permitidos: Necesarios para el CRUD completo
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // C. Cabeceras Permitidas: Authorization, Content-Type, etc.
        configuration.setAllowedHeaders(List.of("*"));
        
        // D. Permitir credenciales (cookies/tokens)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplicar esta configuración a todas las rutas
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}