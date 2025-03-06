package br.com.iuryalmeida.TaskEase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permitir CORS para todos os endpoints
                        .allowedOrigins("http://localhost:5500") // Frontend (simples) acessando
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                        .allowedHeaders("*") // Todos os cabeçalhos permitidos
                        .allowCredentials(true) // Permitir cookies e autenticação
                        .maxAge(3600); // Cache para as configurações de CORS (1 hora)
            }
        };
    }
}
