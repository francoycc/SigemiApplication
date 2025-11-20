package com.sigemi.SigemiApplication.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Habilita CORS para todos los endpoints de tu API
        registry.addMapping("/api/**") 
                // Permite solicitudes desde donde corre tu app de React
                .allowedOrigins("http://localhost:3000") 
                // Permite los métodos HTTP que usas
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") 
                // Permitir todas las cabeceras (necesario para enviar Authorization)
                .allowedHeaders("*")
                // Permitir credenciales (cookies, headers de auth)
                .allowCredentials(true);
    }
}