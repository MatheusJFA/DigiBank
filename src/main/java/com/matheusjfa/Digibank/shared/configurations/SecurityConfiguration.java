package com.matheusjfa.Digibank.shared.configurations;

import com.matheusjfa.Digibank.shared.security.JWTCustomAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JWTCustomAuthenticationFilter jwtCustomAuthenticationFilter
    ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
                            // Permite acesso a URLs públicas
                            authorize.requestMatchers(
                                    "/v2/api-docs/**",
                                    "/v3/api-docs/**",
                                    "/swagger-resources/**",
                                    "/swagger-ui.html",
                                    "/swagger-ui/**",
                                    "/webjars/**",
                                    "/actuator/**"
                            ).permitAll();
                            authorize.anyRequest().authenticated();
                        }
                )
                .addFilterAfter(jwtCustomAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // Remove o prefixo "ROLE_" dos papéis
        return new GrantedAuthorityDefaults("");
    }
}