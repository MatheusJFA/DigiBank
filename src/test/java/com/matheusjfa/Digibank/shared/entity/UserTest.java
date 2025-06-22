package com.matheusjfa.Digibank.shared.entity;

import com.matheusjfa.Digibank.shared.enums.Role;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

	/***
	 * Cenários de Teste:
	 * 1. Criar um usuário com todos os campos válidos.
	 * 2. Tentar criar um usuário com CPF inválido.
	 * 3. Tentar criar um usuário com email inválido.
	 * 4. Verificar se o usuário implementa UserDetails corretamente.
	 * 5. Verificar se o usuário tem o papel correto.
	 * 6. Verificar se o usuário pode ser ativado/desativado.
	 * 7. Atualizar dados do usuário e verificar se a atualização foi bem-sucedida.
	 * 8. Atualizar os dados do usuário com dados inválidos e verificar se a exceção é lançada.
	 */

	@Test
	public void givenValidUser_whenCreateUser_thenUserCreatedSuccessfully() {
		// Arrange
		final var expectedName = "John Doe";
		final var expectedEmail = "john.doe@email.com";
		final var expectedCpf = "12345678909"; // CPF válido
		final var expectedPassword = "password123";
		final var expectedPhone = "1234567890";
		final var expectedBirthDate = LocalDate.of(1990, 1, 1);
		final var expectedRole = Role.USER;

		// Act
		User user = User.create(expectedName, expectedCpf, expectedEmail, expectedPassword, expectedPhone, expectedBirthDate, expectedRole);

		// Assert
		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals(expectedName, user.getName());
		assertEquals(expectedEmail, user.getEmail());
		assertEquals(expectedCpf, user.getCpf());
		assertEquals(expectedPassword, user.getPassword());
		assertEquals(expectedPhone, user.getPhone());
		assertEquals(expectedBirthDate, user.getBirthDate());
		assertEquals(expectedRole, user.getRole());
		assertTrue(user.isActive());
	}
}