package com.MatheusJFA.Digibank.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class AuditingConfiguration {
    @Bean
    public AuditorAware<String> auditorProvider() {
        // Esse método é usado para fornecer o nome do auditor (quem criou ou modificou a entidade)
        // Aqui, estamos verificando se o usuário está autenticado e, se sim, retornamos o nome do usuário para o Spring Security Context.
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UUID)
                return Optional.of("SYSTEM");

            return Optional.ofNullable(authentication.getName());
        };
    }
}
