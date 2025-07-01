package com.MatheusJFA.Digibank.domain.user;

import com.MatheusJFA.Digibank.domain.user.enums.Role;
import com.MatheusJFA.Digibank.domain.valueObject.CPF;
import com.MatheusJFA.Digibank.domain.valueObject.Email;
import com.MatheusJFA.Digibank.domain.valueObject.Phone;
import com.MatheusJFA.Digibank.shared.base.BaseEntity;
import com.MatheusJFA.Digibank.shared.exceptions.InvalidFieldException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Slf4j
public class User extends BaseEntity implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;

    private String passwordHash;

    private Email email;

    private CPF cpf;

    private Phone phone;

    private LocalDate birthDate;

    private boolean isActive;

    private Role role;

    private LocalDateTime lastLogin;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("Obtendo autoridades de {} para o usuário {}", this.role, this.getId());
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    public String getPassword() {
        return "";
    }

    public String getUsername() {
        return this.getId().toString();
    }

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

    // Factory method para criar um novo usuário
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

    public static User with(String name, String passwordHash, String email, String cpf, String phone, LocalDate birthDate, Role role) {
        return new User(name, passwordHash, email, cpf, phone, birthDate, role);
    }

    public static User with(User user) {
        return new User(
                user.getName(),
                user.getPasswordHash(),
                user.getEmail().getValue(),
                user.getCpf().getValue(),
                user.getPhone().getValue(),
                user.getBirthDate(),
                user.getRole()
        );
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
