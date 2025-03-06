package br.com.iuryalmeida.TaskEase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Desabilita o CSRF
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configura o CORS
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Configura a sessão como stateless
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/users/**").permitAll()
                    .requestMatchers("/tasks/**").permitAll()
                    .requestMatchers("/status/**").permitAll()
                    .requestMatchers("/due/**").permitAll()
                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .requestMatchers("/h2-console/**").permitAll() // Permite o acesso ao console H2
                    .anyRequest().authenticated();
            })
            .headers(headers -> headers
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                    XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))); // Permite o uso de frames no mesmo domínio

        return http.build();
    }

    // Configuração do CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Permite todas as origens
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
        configuration.setAllowedHeaders(Arrays.asList("*")); // Headers permitidos
        configuration.setExposedHeaders(Arrays.asList("Authorization")); // Headers expostos

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica a configuração a todas as rotas
        return source;
    }
}