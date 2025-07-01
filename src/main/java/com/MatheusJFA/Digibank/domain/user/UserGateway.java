package com.MatheusJFA.Digibank.domain.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface UserGateway {
    // Cria o usuário
    User save(User user);

    // Busca o usuário por ID ou email
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional<User> findByCpf(String cpf);
    Optional<User> findByPhone(String phone);

    // Busca todos os usuários
    Page<User> findAll(Pageable pageable);

    // Atualiza o usuário
    User update(User user);

    // Deleta o usuário por ID
    void deleteById(UUID id);
}
