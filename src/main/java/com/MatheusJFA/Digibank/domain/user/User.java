package com.MatheusJFA.Digibank.domain.user;

import com.MatheusJFA.Digibank.domain.user.enums.Role;
import com.MatheusJFA.Digibank.domain.valueObject.CPF;
import com.MatheusJFA.Digibank.domain.valueObject.Email;
import com.MatheusJFA.Digibank.domain.valueObject.Phone;
import com.MatheusJFA.Digibank.shared.base.BaseEntity;
import com.MatheusJFA.Digibank.shared.exceptions.InvalidFieldException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users",
        indexes = {
                @Index(name = "idx_users_email", columnList = "email", unique = true),
                @Index(name = "idx_users_cpf", columnList = "cpf", unique = true)
        }
)
@Getter
@NoArgsConstructor
@DynamicUpdate
@Slf4j
public class User extends BaseEntity implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    @Embedded
    private Email email;

    @Embedded
    private CPF cpf;

    @Embedded
    private Phone phone;

    @Column(name="birth_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> this.role.name());
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return this.getId().toString();
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }

    public void activate() {
        this.isActive = true;
        log.info("Usuário {} ativado com sucesso.", this.getId());
    }

    public void deactivate() {
        this.isActive = false;
        log.info("Usuário {} desativado com sucesso.", this.getId());
    }

    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
        log.info("Último login do usuário {} atualizado para {}", this.getId(), this.lastLogin);
    }

    private User(String name, String passwordHash, String email, String cpf, String phone, LocalDate birthDate, Role role) {
        super();
        this.name = name;
        this.passwordHash = passwordHash;
        this.email = new Email(email);
        this.cpf = new CPF(cpf);
        this.phone = new Phone(phone);
        this.birthDate = birthDate;
        this.isActive = true; // Usuário ativo por padrão
        this.role = role;
        log.info("Usuário {} criado com sucesso.", this.getId());
    }

    public static User create(String name, String passwordHash, String email, String cpf, String phone, LocalDate birthDate, Role role) {
        return new User(name, passwordHash, email, cpf, phone, birthDate, role);
    }

    public User update(String name, String email, String cpf, String phone, LocalDate birthDate) {
        validateParameters(name, email, cpf, phone, birthDate);
        this.name = name;
        changeCPF(cpf);
        changeEmail(email);
        changePhone(phone);
        changeBirthDate(birthDate);

        log.info("Usuário {} atualizado com sucesso.", this.getId());
        return this;
    }

    private void validateParameters(Object... parameters)  {
        for (Object parameter : parameters) {
            if (parameter == null || (parameter instanceof String && ((String) parameter).isBlank())) {
                throw new InvalidFieldException("Não pode fazer a atualização com valores nulos ou vazios");
            }
        }
    }

    public void changePassword(String newPassword) {
        // A senha deve ser criptografada antes de ser definida
        validateParameters(newPassword);
        this.passwordHash = newPassword;
        log.info("Senha do usuário {} alterada com sucesso.", this.getId());
    }

    public void changeEmail(String newEmail) {
        validateParameters(newEmail);
        this.email = new Email(newEmail);
        log.info("E-mail do usuário {} alterado com sucesso.", this.getId());
    }

    public void changePhone(String newPhone) {
        validateParameters(newPhone);
        this.phone = new Phone(newPhone);
        log.info("Telefone do usuário {} alterado com sucesso.", this.getId());
    }

    public void changeCPF(String newCPF) {
        validateParameters(newCPF);
        this.cpf = new CPF(newCPF);
        log.info("CPF do usuário {} alterado com sucesso.", this.getId());
    }

    public void changeBirthDate(LocalDate newBirthDate) {
        validateParameters(newBirthDate);
        this.birthDate = newBirthDate;
        log.info("Data de nascimento do usuário {} alterada com sucesso.", this.getId());
    }
}
