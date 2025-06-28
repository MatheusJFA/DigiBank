package com.MatheusJFA.Digibank.domain.valueObject;

import com.MatheusJFA.Digibank.shared.exceptions.InvalidEmailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {

    /**
     * Cenário de teste:
     * 1. Dado um email válido, o objeto Email deve ser criado com sucesso.
     * 2. Dado um email inválido, o objeto Email deve lançar uma exceção.
     * 3. Dado um email vazio ou nulo, o objeto Email deve lançar uma exceção.
     * 4. Dado um email com Caractéres especiais inválidos ou formato incorreto, o objeto Email deve lançar uma exceção.
     * 5. Dado um email com domínio inválido, o objeto Email deve lançar uma exceção.
     * 6. Dado um email válido ao chamar a função getDomain(), o domínio deve ser retornado corretamente.
     */

    @Test
    public void givenValidEmail_whenCreatingEmail_thenShouldCreateSuccessfully() {
        final var expectedEmail = "test@domain.com";

        Email emailObject = new Email(expectedEmail);

        assertNotNull(emailObject);
        assertEquals(expectedEmail, emailObject.getValue());
    }


    @Test
    public void givenInvalidEmail_whenCreatingEmail_thenShouldThrowException() {
        final var invalidEmail = "invalid-email@domain";

        final var exception = assertThrows(InvalidEmailException.class, () -> {
            new Email(invalidEmail);
        });

        String expectedMessage = "O Email informado é inválido";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void givenNullOrEmptyEmail_whenCreatingEmail_thenShouldThrowException(String email) {
        final var exception = assertThrows(InvalidEmailException.class, () -> {
            new Email(email);
        });

        String expectedMessage = "O Email não pode ser nulo ou vazio";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "invalid-email",
            "@invalid.com",
            "invalid@.com",
            "invalid#@$@domain",
            "invalid$@domain.",
            "invalid!@@domain..com",
            "invalid)(@domain,com",
            "_@domain;com"
    })
    public void givenEmailWithInvalidCharacters_whenCreatingEmail_thenShouldThrowException(String email) {
        final var exception = assertThrows(InvalidEmailException.class, () -> {
            new Email(email);
        });

        String expectedMessage = "O Email informado é inválido";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "invalid@domain,com",
            "invalid@domain;com",
            "invalid@domain..com",
            "invalid@domain.",
            "invalid@.com"
    })
    public void givenEmailWithInvalidDomain_whenCreatingEmail_thenShouldThrowException(String email) {
        final var exception = assertThrows(InvalidEmailException.class, () -> {
            new Email(email);
        });

        String expectedMessage = "O Email informado é inválido";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenValidEmail_whenGettingDomain_thenShouldReturnCorrectDomain() {
        final var expectedEmail = "test@domain.com";

        final var email = new Email(expectedEmail);

        assertNotNull(email);
        String expectedDomain = "domain.com";
        assertEquals(expectedDomain, email.getDomain());
    }
}