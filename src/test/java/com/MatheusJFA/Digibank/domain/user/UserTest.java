package com.MatheusJFA.Digibank.domain.user;

import com.MatheusJFA.Digibank.domain.user.enums.Role;
import com.MatheusJFA.Digibank.shared.exceptions.InvalidCPFException;
import com.MatheusJFA.Digibank.shared.exceptions.InvalidEmailException;
import com.MatheusJFA.Digibank.shared.exceptions.InvalidFieldException;
import com.MatheusJFA.Digibank.shared.exceptions.InvalidPhoneException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    /***
     * Cenários de Teste:
     * 1. Criar um usuário com todos os campos válidos.
     * 2. Tentar criar um usuário com CPF inválido.
     * 3. Tentar criar um usuário com email inválido.
     * 4. Tentar criar um usuário com telefone inválido.
     * 5. Verificar se o usuário implementa UserDetails corretamente.
     * 6. Verificar se o usuário tem o papel correto.
     * 7. Verificar se o usuário pode ser ativado/desativado.
     * 8. Atualizar dados do usuário e verificar se a atualização foi bem-sucedida.
     * 9. Atualizar os dados do usuário com dados inválidos e verificar se a exceção é lançada.
     */

    @Test
    public void givenValidUser_whenCreateUser_thenUserCreatedSuccessfully() {
        // Arrange
        final var expectedName = "John Doe";
        final var expectedEmail = "john.doe@email.com";
        final var expectedCpf = "12345678909"; // CPF válido
        final var expectedPassword = "password123";
        final var expectedPhone = "+55 (31) 12345-6789";
        final var expectedBirthDate = LocalDate.of(1990, 1, 1);
        final var expectedRole = Role.USER;

        // Act
        User user = User.create(expectedName, expectedPassword, expectedEmail, expectedCpf, expectedPhone, expectedBirthDate, expectedRole);

        final var unmaskedPhone = "5531123456789";

        // Assert
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(expectedName, user.getName());
        assertEquals(expectedEmail, user.getEmail().getValue());
        assertEquals(expectedCpf, user.getCpf().getValue());
        assertEquals(unmaskedPhone, user.getPhone().getValue());
        assertEquals(expectedPassword, user.getPasswordHash());
        assertEquals(expectedBirthDate, user.getBirthDate());
        assertEquals(expectedRole, user.getRole());
        assertTrue(user.isActive());
    }

    @Test
    public void givenInvalidCpf_whenCreateUser_thenThrowsInvalidParameterException() {
        // Arrange
        final var invalidCpf = "12345678900"; // CPF inválido
        final var expectedName = "John Doe";
        final var expectedEmail = "john@email.com";
        final var expectedPassword = "password123";
        final var expectedPhone = "+55 (31) 12345-6789";
        final var expectedBirthDate = LocalDate.of(1990, 1, 1);
        final var expectedRole = Role.USER;

        // Act & Assert
        Exception exception = assertThrows(InvalidCPFException.class, () -> {
            User.create(expectedName, expectedPassword, expectedEmail, invalidCpf, expectedPhone, expectedBirthDate, expectedRole);        });

        var expectedMessage = "O CPF informado é inválido";
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenInvalidEmail_whenCreateUser_thenThrowsInvalidParameterException() {
        // Arrange
        final var expectedName = "John Doe";
        final var expectedCpf = "12345678909"; // CPF válido
        final var invalidEmail = "invalid-email"; // Email inválido
        final var expectedPassword = "password123";
        final var expectedPhone = "+55 (31) 12345-6789";
        final var expectedBirthDate = LocalDate.of(1990, 1, 1);
        final var expectedRole = Role.USER;

        // Act & Assert
        Exception exception = assertThrows(InvalidEmailException.class, () -> {
            User.create(expectedName, expectedPassword, invalidEmail, expectedCpf, expectedPhone, expectedBirthDate, expectedRole);
        });

        var expectedMessage = "O Email informado é inválido";
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenInvalidPhone_whenCreateUser_thenThrowsInvalidParameterException() {
        // Arrange
        final var expectedName = "John Doe";
        final var expectedCpf = "12345678909"; // CPF válido
        final var expectedEmail = "johnDoe@email.com";
        final var invalidPhone = "123456789"; // Telefone inválido
        final var expectedPassword = "password123";
        final var expectedBirthDate = LocalDate.of(1990, 1, 1);
        final var expectedRole = Role.USER;
        // Act & Assert
        Exception exception = assertThrows(InvalidPhoneException.class, () -> {
            User.create(expectedName, expectedPassword, expectedEmail, expectedCpf, invalidPhone, expectedBirthDate, expectedRole);
        });

        var expectedMessage = "O telefone deve estar no formato +DDI (DDD) 9999-9999 ou +DDI (DDD) 99999-9999.";
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenUser_whenCheckRole_thenRoleIsCorrect() {
        // Arrange
        final var expectedName = "John Doe";
        final var expectedCpf = "12345678909"; // CPF válido
        final var expectedEmail = "johnDoe@email.com"; // Email inválido
        final var expectedPassword = "password123";
        final var expectedPhone = "+55 (31) 12345-6789";
        final var expectedBirthDate = LocalDate.of(1990, 1, 1);
        final var expectedRole = Role.USER;

        // Act
        User user = User.create(expectedName, expectedPassword, expectedEmail, expectedCpf, expectedPhone, expectedBirthDate, expectedRole);

        // Assert
        assertNotNull(user);
        assertEquals(expectedRole, user.getRole());
    }

    @Test
    public void givenUser_whenActivate_thenUserIsActive() {
        // Arrange
        final var expectedName = "John Doe";
        final var expectedCpf = "12345678909"; // CPF válido
        final var expectedEmail = "johnDoe@email.com"; // Email inválido
        final var expectedPassword = "password123";
        final var expectedPhone = "+55 (31) 12345-6789";
        final var expectedBirthDate = LocalDate.of(1990, 1, 1);
        final var expectedRole = Role.USER;

        // Act
        User user = User.create(expectedName, expectedPassword, expectedEmail, expectedCpf, expectedPhone, expectedBirthDate, expectedRole);

        // Assert
        // Checar se o usuário está ativo por padrão
        assertNotNull(user);
        assertTrue(user.isActive());

        // Desativar o usuário
        user.deactivate();
        assertFalse(user.isActive());

        // Ativar o usuário novamente
        user.activate();
        assertTrue(user.isActive());
    }

    @Test
    public void givenUser_whenUpdateUser_thenUserUpdatedSuccessfully() {
        // Arrange
        final var expectedName = "John Doe";
        final var expectedCpf = "12345678909"; // CPF válido
        final var expectedEmail = "johnDoe@email.com";
        final var expectedPassword = "password123";
        final var expectedPhone = "+55 (31) 12345-6789";
        final var expectedBirthDate = LocalDate.of(1990, 1, 1);
        final var expectedRole = Role.USER;

        final var unmaskedOldPhone = "5531123456789";

        User user = User.create(expectedName, expectedPassword, expectedEmail, expectedCpf, expectedPhone, expectedBirthDate, expectedRole);

        // Verificar se o usuário foi criado corretamente
        assertNotNull(user);
        assertEquals(expectedName, user.getName());
        assertEquals(expectedEmail, user.getEmail().getValue());
        assertEquals(expectedCpf, user.getCpf().getValue());
        assertEquals(unmaskedOldPhone, user.getPhone().getValue());
        assertEquals(expectedBirthDate, user.getBirthDate());
        assertEquals(expectedRole, user.getRole());
        assertTrue(user.isActive());

        final var newName = "Jane Doe";
        final var newCpf = "32203478004"; // Novo CPF válido
        final var newEmail = "janeDoe@email.com";
        final var newPhone = "+55 (31) 98765-4321";

        final var newBirthDate = LocalDate.of(1995, 5, 5);

        // Act
        User updatedUser = user.update(newName, newEmail, newCpf, newPhone, newBirthDate);

        final var unmaskedNewPhone = "5531987654321";

        // Assert
        assertNotNull(updatedUser);
        assertEquals(user.getId(), updatedUser.getId()); // ID deve permanecer o mesmo
        assertEquals(newName, updatedUser.getName());
        assertEquals(newCpf, updatedUser.getCpf().getValue());
        assertEquals(newEmail, updatedUser.getEmail().getValue());
        assertEquals(unmaskedNewPhone, updatedUser.getPhone().getValue());
        assertEquals(newBirthDate, updatedUser.getBirthDate());
        assertEquals(expectedRole, updatedUser.getRole()); // Papel deve permanecer o mesmo
        assertTrue(updatedUser.isActive()); // O usuário deve continuar ativo
    }

    @ParameterizedTest
    @CsvSource({
            ", 98765432100, janeDoe@email.com, +55 (31) 98765-4321, 1995-05-05", // Nome vazio (nulo será convertido para empty)
            "'', 98765432100, janeDoe@email.com, +55 (31) 98765-4321, 1995-05-05", // Nome explicitamente vazio
            "Jane Doe,, janeDoe@email.com, +55 (31) 98765-4321, 1995-05-05", // CPF vazio
            "Jane Doe,'', janeDoe@email.com, +55 (31) 98765-4321, 1995-05-05", // CPF explicitamente vazio
            "Jane Doe, 98765432100,, +55 (31) 98765-4321, 1995-05-05", // Email vazio
            "Jane Doe, 98765432100,'', +55 (31) 98765-4321, 1995-05-05", // Email explicitamente vazio
            "Jane Doe, 98765432100, janeDoe@email.com,, 1995-05-05", // Telefone vazio
            "Jane Doe, 98765432100, janeDoe@email.com,'', 1995-05-05", // Telefone explicitamente vazio
    })
    public void givenUser_whenUpdateWithInvalidData_thenThrowsException(String newName, String newCpf, String newEmail, String newPhone, String newBirthDate) {
        // Arrange
        final var expectedName = "John Doe";
        final var expectedCpf = "12345678909"; // CPF válido
        final var expectedEmail = "johnDoe@email.com";
        final var expectedPassword = "password123";
        final var expectedPhone = "+55 (31) 12345-6789";
        final var expectedBirthDate = LocalDate.of(1990, 1, 1);
        final var expectedRole = Role.USER;

        User user = User.create(expectedName, expectedPassword, expectedEmail, expectedCpf, expectedPhone, expectedBirthDate, expectedRole);

        final var unmaskedOldPhone = "5531123456789";

        assertNotNull(user);
        assertEquals(expectedName, user.getName());
        assertEquals(expectedCpf, user.getCpf().getValue());
        assertEquals(expectedEmail, user.getEmail().getValue());
        assertEquals(unmaskedOldPhone, user.getPhone().getValue());
        assertEquals(expectedBirthDate, user.getBirthDate());
        assertEquals(expectedRole, user.getRole());
        assertTrue(user.isActive());


        final var exception = assertThrows(InvalidFieldException.class, () -> {
            user.update(newName, newEmail, newCpf, newPhone, LocalDate.parse(newBirthDate));
        });

        // Assert
        assertNotNull(exception);
        String expectedMessage = "Não pode fazer a atualização com valores nulos ou vazios";
        assertEquals(expectedMessage, exception.getMessage());
    }
}