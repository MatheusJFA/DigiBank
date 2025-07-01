package com.MatheusJFA.Digibank.infrastructure.modules.user.persistence;

import com.MatheusJFA.Digibank.domain.user.User;
import com.MatheusJFA.Digibank.domain.user.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
public class UserJPA {
    // Essa classe deve conter os campos e métodos necessários para mapear a entidade User
    // para o banco de dados, utilizando JPA (Java Persistence API).

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "version")
    private Long version;

    public UserJPA() {
        // Construtor padrão necessário para JPA
    }

    public static UserJPA from(User user) {
        UserJPA userJPA = new UserJPA();
        userJPA.id = user.getId();
        userJPA.name = user.getName();
        userJPA.passwordHash = user.getPasswordHash();
        userJPA.email = user.getEmail().getValue();
        userJPA.cpf = user.getCpf().getValue();
        userJPA.phone = user.getPhone().getValue();
        userJPA.birthDate = user.getBirthDate();
        userJPA.isActive = user.isActive();
        userJPA.role = user.getRole().name();
        userJPA.lastLogin = user.getLastLogin();

        userJPA.createdBy = user.getCreatedBy();
        userJPA.createdDate = user.getCreatedDate();
        userJPA.lastModifiedBy = user.getLastModifiedBy();
        userJPA.lastModifiedDate = user.getLastModifiedDate();
        userJPA.version = user.getVersion();

        return userJPA;
    }

    public User toDomain() {
        return User.with(
            this.name,
            this.passwordHash,
            this.email,
            this.cpf,
            this.phone,
            this.birthDate,
            Role.valueOf(this.role)
        );
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role));
    }
}
