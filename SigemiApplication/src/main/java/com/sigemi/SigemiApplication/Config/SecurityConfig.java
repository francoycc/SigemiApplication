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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            // Activar CORS usando nuestra configuración
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            // Configurar Permisos de URL
            .authorizeHttpRequests(auth -> auth
                // PERMITIR TODO en /api/auth/** (Login, Registro, etc.)
                .requestMatchers("/api/auth/**").permitAll()
                // (Opcional) Permitir acceso a recursos estáticos si los hubiera
                .requestMatchers("/error").permitAll()
                // BLOQUEAR el resto (requiere autenticación)
                // Por ahora, para facilitar tus pruebas, podrías usar .permitAll() aquí también
                // pero lo correcto es .authenticated()
                .anyRequest().authenticated() 
            );

        return http.build();
    }

    // Configuracion CORS para Spring Security
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Permitir el origen del Frontend
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        
        // Permitir todos los métodos HTTP (GET, POST, PUT, DELETE, OPTIONS)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        
        // Permitir todos los headers (Authorization, Content-Type, etc.)
        configuration.setAllowedHeaders(List.of("*"));
        
        // Permitir credenciales (cookies, headers de auth)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}