package com.matheusjfa.Digibank.shared.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfiguration {
    @Value("${cors.allowed-origins}")
    private String ALLOWED_ORIGIN;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        configuration.addAllowedOrigin(List.of(ALLOWED_ORIGIN).toString());
        configuration.addAllowedMethod(List.of(
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
        ).toString());
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        return request -> configuration;
    }
}
