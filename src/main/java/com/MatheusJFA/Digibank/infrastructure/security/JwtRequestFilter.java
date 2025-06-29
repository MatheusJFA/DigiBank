package com.MatheusJFA.Digibank.infrastructure.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String id = null;
        String jwtToken = null;

        // JWT Token é uma forma de autenticação que é passada no cabeçalho da requisição
        if (isAuthenticated(requestTokenHeader)) {
            jwtToken = getToken(requestTokenHeader);
            try {
                id = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                log.error("Não foi possível obter o JWT Token: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Não foi possível obter o JWT Token");
            } catch (ExpiredJwtException e) {
                log.warn("O JWT Token expirou: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "O JWT Token expirou");
            } catch (Exception e) {
                log.error("Erro na validação do JWT Token: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT Token inválido");
            }
        } else {
            log.warn("O JWT Token não começa com Bearer ou está ausente");
        }

        authenticate(request, id, jwtToken);

        filterChain.doFilter(request, response);
    }

    private void authenticate(HttpServletRequest request, String id, String jwtToken) {
        if (id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(id);

            // Se o token é válido, cria um UsernamePasswordAuthenticationToken
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                log.debug("Usuário '{}' autenticado com sucesso.", id);
            } else {
                log.warn("Falha na validação do JWT Token para o usuário: {}", id);
            }
        }
    }

    private static boolean isAuthenticated(String requestTokenHeader) {
        return requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ");
    }

    private static String getToken(String requestTokenHeader) {
        return requestTokenHeader.substring(7);
    }
}