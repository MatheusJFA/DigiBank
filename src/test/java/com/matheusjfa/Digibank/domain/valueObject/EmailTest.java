package com.matheusjfa.Digibank.domain.valueObject;

import com.matheusjfa.Digibank.shared.domain.exceptions.InvalidEmailException;
import com.matheusjfa.Digibank.shared.domain.valueObject.Email;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {
    /**
     * 1 Cenário - Sendo utilizado um email válido a aplicação não deve apresentar erros e deve retornar um Objeto Email
     * 2 Cenário - Sendo utilizado um email inválido a aplicação deve lançar um erro de InvalidEmailException
     * 3 Cenário - Sendo utilizado um email vazio a aplicação deve lançar um erro de InvalidEmailException
     * 4 Cenário - Sendo utilizado um email nulo a aplicação deve lançar um erro de InvalidEmailException
     * 5 Cenário - Sendo utilizado um email com caracteres inválidos (caracteres especiais e acentuações) a aplicação deve lançar um erro de InvalidEmailException
     * 6 Cenário - Sendo chamado o método getDomain deve retornar o domínio do email
     * 7 Cenário - Sendo chamado o getValue deve retornar o email completo
     * 8 Cenário - Sendo chamado o método toString deve retornar o email completo
     */

    @Test
    public void givenAValidEmail_whenCreatingIt_thenShouldReturnAValidEmail() {
        final String expectedEmail = "teste@email.com";
        final var email = new Email(expectedEmail);
        assertNotNull(email);
    }

    @Test
    public void givenAnInvalidEmail_whenCreatingIt_thenShouldThrowAnException() {
        final String invalidEmail = "invalid-email";
        var exception = assertThrows(InvalidEmailException.class, () -> new Email(invalidEmail));

        assertNotNull(exception);
        final String expectedMessage = "O email informado é inválido";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenAnEmptyEmail_whenCreatingIt_thenShouldThrowAnException() {
        final String emptyEmail = "";
        var exception = assertThrows(InvalidEmailException.class, () -> new Email(emptyEmail));

        assertNotNull(exception);
        final String expectedMessage = "O email informado é nulo ou vazio";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenANullEmail_whenCreatingIt_thenShouldThrowAnException() {
        final String nullEmail = null;
        var exception = assertThrows(InvalidEmailException.class, () -> new Email(nullEmail));

        assertNotNull(exception);
        final String expectedMessage = "O email informado é nulo ou vazio";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "test@émail.com", // Acentuação inválida
            "test@.com", // Ponto no início do domínio
            "test@email..com", // Dois pontos seguidos
            "test@-email.com", // Hífen no início do domínio
            "test@email-.com", // Hífen no final do domínio
            "test@.email.com", // Ponto no início do domínio
            "test@email..com" // Dois pontos seguidos no domínio
    })
    public void givenAnEmailWithInvalidCharacters_whenCreatingIt_thenShouldThrowAnException(String invalidEmail) {
        var exception = assertThrows(InvalidEmailException.class, () -> new Email(invalidEmail));
        assertNotNull(exception);
        final String expectedMessage = "O email informado é inválido";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenAValidEmail_whenGettingDomain_thenShouldReturnDomain() {
        final String expectedEmail = "teste@email.com";
        final var email = new Email(expectedEmail);
        String domain = email.getDomain();
        String expectedDomain = "email.com";
        assertEquals(expectedDomain, domain);
    }

    @Test
    public void givenAValidEmail_whenGettingValue_thenShouldReturnEmail() {
        final String expectedEmail = "test@email.com";
        final var email = new Email(expectedEmail);
        String value = email.getValue();
        assertEquals(expectedEmail, value);
    }

    @Test
    public void givenAValidEmail_whenToString_thenShouldReturnEmail() {
        final String expectedEmail = "test@email.com";
        final var email = new Email(expectedEmail);
        String value = email.toString();
        assertEquals(expectedEmail, value);
    }
}
