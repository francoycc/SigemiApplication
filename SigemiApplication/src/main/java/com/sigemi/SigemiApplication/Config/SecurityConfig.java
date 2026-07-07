package com.sigemi.SigemiApplication.Config;

import com.sigemi.SigemiApplication.Security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desactivar CSRF: Obligatorio para APIs REST sin cookies de sesión
            .csrf(csrf -> csrf.disable())
            
            // 2. Configurar CORS: Enlaza el Bean de abajo para aceptar peticiones desde React
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            
            // 3. MEJORA CRÍTICA: Definir la política de sesión como STATELESS (Sin Estado)
            // Esto evita que Spring intente crear sesiones HTTP en el servidor y optimiza la memoria RAM.
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // 4. Gestión de Permisos de URL (Diccionario de Autoridades)
            .authorizeHttpRequests(auth -> auth
                // Permite que el navegador consulte los CORS libremente (Preflight requests)
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Autenticación libre (Registro / Login)
                .requestMatchers("/api/auth/**").permitAll()

                // Permisos de Lectura Globales (Selects y catálogos requeridos por los formularios)
                .requestMatchers(HttpMethod.GET, "/api/usuarios/**", "/api/repuestos/**", "/api/ubicaciones/**", "/api/equipos/**")
                    .hasAnyAuthority("ADMINISTRADOR", "ROLE_ADMINISTRADOR", "SUPERVISOR", "ROLE_SUPERVISOR", "OPERARIO", "ROLE_OPERARIO")

                // Módulo de Equipos y Ubicaciones (Modificaciones exclusivas)
                .requestMatchers(HttpMethod.POST, "/api/equipos/**", "/api/ubicaciones/**").hasAnyAuthority("SUPERVISOR", "ROLE_SUPERVISOR", "ADMINISTRADOR", "ROLE_ADMINISTRADOR")
                .requestMatchers(HttpMethod.PUT, "/api/equipos/**", "/api/ubicaciones/**").hasAnyAuthority("SUPERVISOR", "ROLE_SUPERVISOR", "ADMINISTRADOR", "ROLE_ADMINISTRADOR")
                .requestMatchers(HttpMethod.DELETE, "/api/equipos/**", "/api/ubicaciones/**").hasAnyAuthority("SUPERVISOR", "ROLE_SUPERVISOR", "ADMINISTRADOR", "ROLE_ADMINISTRADOR")

                // Órdenes de Mantenimiento 
                .requestMatchers("/api/ordenes/nueva", "/api/ordenes/editar/**").hasAnyAuthority("SUPERVISOR", "ROLE_SUPERVISOR")
                .requestMatchers(HttpMethod.GET, "/api/ordenes/**").hasAnyAuthority("OPERARIO", "ROLE_OPERARIO", "SUPERVISOR", "ROLE_SUPERVISOR", "ADMINISTRADOR", "ROLE_ADMINISTRADOR")

                // Tareas Técnicas y Ejecución
                .requestMatchers("/api/tareas/**").hasAnyAuthority("OPERARIO", "ROLE_OPERARIO", "SUPERVISOR", "ROLE_SUPERVISOR", "ADMINISTRADOR", "ROLE_ADMINISTRADOR")

                // Auditoría y Logs (Exclusivo Administradores)
                .requestMatchers("/api/logs/**").hasAnyAuthority("ADMINISTRADOR", "ROLE_ADMINISTRADOR")

                // Cualquier otra ruta no especificada requerirá autenticación
                .anyRequest().authenticated()
            );

        // 5. MEJORA CRÍTICA: Enganchar tu filtro de autenticación (JWT) antes del procesador por defecto de Spring.
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Configuración detallada de CORS 
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // A. Origen Permitido: El puerto por defecto del servidor de desarrollo de React
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        
        // B. Métodos HTTP Permitidos: CRUD Completo e inspección OPTIONS
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // C. Cabeceras Permitidas: Permite pasar Authorization (Tokens), Content-Type, etc.
        configuration.setAllowedHeaders(List.of("*"));
        
        // D. Permitir credenciales cruzadas
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}