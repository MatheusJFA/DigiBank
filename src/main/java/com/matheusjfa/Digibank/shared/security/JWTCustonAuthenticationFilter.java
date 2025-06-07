package com.matheusjfa.Digibank.shared.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTCustonAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // Aqui você pode implementar a lógica de autenticação JWT
        // Por exemplo, verificar o token JWT no cabeçalho Authorization

        String authorizationHeader = request.getHeader("Authorization");
        if (isBearer(authorizationHeader)) {
            String jwtToken = authorizationHeader.substring(7);
            // Validar o token JWT e configurar a autenticação no contexto de segurança
        }

        filterChain.doFilter(request, response);
    }

    private static boolean isBearer(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer ");
    }
}
