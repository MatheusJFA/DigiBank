package com.MatheusJFA.Digibank.infrastructure.modules.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserJPA, UUID> {
    Optional<UserJPA> findByEmail(String email);
    Optional<UserJPA> findByCpf(String cpf);
}
