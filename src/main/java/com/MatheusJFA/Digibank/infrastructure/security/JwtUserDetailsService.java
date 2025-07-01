package com.MatheusJFA.Digibank.infrastructure.security;

import com.MatheusJFA.Digibank.domain.user.UserGateway;
import com.MatheusJFA.Digibank.infrastructure.modules.user.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        final var user = userRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> {
                    log.error("Usuário não encontrado com o ID: {}", id);
                    return new UsernameNotFoundException("Usuário não encontrado com o ID: " + id);
                });

        return new User(
                user.getId().toString(),
                null,
                user.getAuthorities()
        );
    }
}
