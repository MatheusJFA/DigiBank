package com.matheusjfa.Digibank.shared.entity;

import com.matheusjfa.Digibank.shared.enums.Role;
import com.matheusjfa.Digibank.shared.exceptions.InvalidParameterException;
import com.matheusjfa.Digibank.shared.valueObjects.CPF;
import com.matheusjfa.Digibank.shared.valueObjects.Email;
import com.matheusjfa.Digibank.shared.valueObjects.Phone;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users",
				indexes = {
								@Index(name = "idx_users_email", columnList = "email", unique = true),
								@Index(name = "idx_users_cpf", columnList = "cpf", unique = true)
				}
)
public class User extends Auditory implements UserDetails {
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false, length = 255)
	private String name;

	@Embedded
	@Column(nullable = false, length = 11, unique = true)
	private CPF cpf;

	@Embedded
	@Column(nullable = false, length = 255, unique = true)
	private Email email;

	@Column(nullable = false)
	private String passwordHash;

	@Embedded
	@Column(length = 20)
	private Phone phone;

	@Column(nullable = false)
	private LocalDate birthDate;

	@Column(nullable = false)
	private boolean active;

	@Column(nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private Role role;

	private LocalDateTime lastLogin;

	private User(UUID id, String name, CPF cpf, Email email, String passwordHash, Phone phone, LocalDate birthDate, boolean active, Role role, LocalDateTime lastLogin) {
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.email = email;
		this.passwordHash = passwordHash;
		this.phone = phone;
		this.birthDate = birthDate;
		this.active = active;
		this.role = role;
		this.lastLogin = lastLogin;
	}

	public static User create(String name, String cpfValue, String emailValue, String passwordHash, String phoneValue, LocalDate birthDate, Role role) {
		var id = UUID.randomUUID();
		var cpf = new CPF(cpfValue);
		var email = new Email(emailValue);
		var phone = new Phone(phoneValue);

		var isActive = true; // Default value for new users

		return new User(id, name, cpf, email, passwordHash, phone, birthDate, isActive, role, null);
	}

	public User update(String name, String cpf, String email, String phone, LocalDate birthDate) {
		validateParameters(name, cpf, email, phone, birthDate);
		this.name = name;
		this.cpf = new CPF(cpf);
		this.email = new Email(email);
		this.phone = new Phone(phone);
		this.birthDate = birthDate;

		return this;
	}

	public void changePassword(String newPasswordHash) {
		this.passwordHash = newPasswordHash;
	}

	public void updateRole(Role newRole) {
		this.role = newRole;
	}

	public void updateLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void activate() {
		this.active = true;
	}

	public void deactivate() {
		this.active = false;
	}

	private void validateParameters(Object... parameters)  {
		for (Object parameter : parameters) {
			if (parameter == null || (parameter instanceof String && ((String) parameter).isBlank())) {
				throw new InvalidParameterException("Não pode fazer a atualização com valores nulos ou vazios");
			}
		}
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCpf() {
		return cpf.getValue();
	}

	public String getEmail() {
		return email.getValue();
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getPhone() {
		return phone.getValue();
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public boolean isActive() {
		return active;
	}

	public Role getRole() {
		return role;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(() -> role.name());
	}

	@Override
	public String getPassword() {
		return this.passwordHash;
	}

	@Override
	public String getUsername() {
		return this.cpf.getValue();
	}

	@Override
	public boolean isEnabled() {
		return this.active;
	}
}
