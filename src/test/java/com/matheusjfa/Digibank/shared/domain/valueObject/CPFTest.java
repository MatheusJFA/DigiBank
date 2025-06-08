package com.matheusjfa.Digibank.shared.domain.valueObject;

import com.matheusjfa.Digibank.shared.domain.exceptions.InvalidCPFException;
import com.matheusjfa.Digibank.shared.domain.valueObject.CPF;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPFTest {
    /**
     * 1 Cenário - Sendo utilizado um CPF válido a aplicação não deve apresentar erros e deve retornar um Objeto CPF
     * 2 Cenário - Sendo utilizado um CPF válido porém mascardo a aplicação não deve apresentar erros e deve retornar um Objeto CPF
     * 3 Cenário - Sendo enviado um CPF em branco a aplicação deve lançar um erro de InvalidCPFException
     * 4 Cenário - Sendo enviado um CPF nulo a aplicação deve lançar um erro de InvalidCPFException
     * 5 Cenário - Sendo enviado um CPF incompleto a aplicação deve lançar um erro de InvalidCPFException
     * 6 Cenário - Sendo enviado um CPF com caracteres inválidos (letras e acentuações) a aplicação deve lançar um erro de InvalidCPFException
     * 7 Cenário - Sendo enviado um CPF com caracteres errados a aplicação deve lançar um erro de InvalidCPFException
     * 8 Cenário - Sendo chamada a função mask deve retonar um CPF mascarado no padrão XXX.XXX.XXX-XX
     * 9 Cenário - Sendo chamada a função unmask deve retornar o CPF desmascarado (apenas digitos)
     * 10 Cenário - Sendo chamada a função toString deve retornar o CPF mascarado no padrão XXX.XXX.XXX-XX
     * 11 Cenário - Sendo chamada a função getValue deve retornar o CPF desmascarado (apenas digitos)
     * 12 Cenário - Sendo chamada a função mask com um CPF nulo deve lançar um erro de InvalidCPFException
     * 13 Cenário - Sendo chamada a função unmask com um CPF nulo deve lançar um erro de InvalidCPFException
     */

    @Test
    public void givenAValidCPF_whenCreatingIt_thenShouldReturnAValidCPF() {
        final var expectedCPF = "12345678909";
        final var cpf = new CPF(expectedCPF);

        assertNotNull(cpf);
    }

    @Test
    public void givenAValidMaskedCPF_whenCreatingIt_thenShouldReturnAValidCPF() {
        final String maskedCPF = "123.456.789-09";
        final var cpf = new CPF(maskedCPF);

        assertNotNull(cpf);
    }

    @Test
    public void givenAnEmptyCPF_whenCreatingIt_thenShouldThrowAnException() {
        final var expectedCPF = "";
        var exception = assertThrows(InvalidCPFException.class, () -> new CPF(expectedCPF));

        assertNotNull(exception);
        final var expectedMessage = "O CPF informado é nulo ou vazio";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenANullCPF_whenCreatingIt_thenShouldThrowAnException() {
        final String expectedCPF = null;
        var exception = assertThrows(InvalidCPFException.class, () -> new CPF(expectedCPF));

        assertNotNull(exception);
        final var expectedMessage = "O CPF informado é nulo ou vazio";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenAnIncompleteCPF_whenCreatingIt_thenShouldThrowAnException() {
        final var expectedCPF = "123456789";
        var exception = assertThrows(InvalidCPFException.class, () -> new CPF(expectedCPF));

        assertNotNull(exception);
        final var expectedMessage = "O CPF informado não está no padrão esperado";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenAnInvalidCharactersCPF_whenCreatingIt_thenShouldThrowAnException() {
        final var expectedCPF = "sabc821344sas";
        var exception = assertThrows(InvalidCPFException.class, () -> new CPF(expectedCPF));

        assertNotNull(exception);
        final var expectedMessage = "O CPF informado não está no padrão esperado";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenAnInvalidCPF_whenCreatingIt_thenShouldThrowAnException() {
        final var expectedCPF = "12345678900";
        var exception = assertThrows(InvalidCPFException.class, () -> new CPF(expectedCPF));

        assertNotNull(exception);
        final var expectedMessage = "O CPF informado não é válido";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenAValidCPF_whenMaskingIt_thenShouldReturnMaskedCPF() {
        final var expectedCPF = "12345678909";
        final var expectedMaskedCPF = "123.456.789-09";
        final var cpf = new CPF(expectedCPF);
        final var maskedCPF = cpf.mask();

        assertNotNull(maskedCPF);
        assertEquals(expectedMaskedCPF, maskedCPF);
    }

    @Test
    public void givenAMaskedCPF_whenUnmaskingIt_thenShouldReturnUnmaskedCPF() {
        final var expectedMaskedCPF = "123.456.789-09";
        final var expectedUnmaskedCPF = "12345678909";
        final var cpf = new CPF(expectedMaskedCPF);
        final var unmaskedCPF = cpf.unmask();

        assertNotNull(unmaskedCPF);
        assertEquals(expectedUnmaskedCPF, unmaskedCPF);
    }

    @Test
    public void givenAValidCPF_whenToString_thenShouldReturnMaskedCPF() {
        final var expectedMaskedCPF = "123.456.789-09";
        final var cpf = new CPF(expectedMaskedCPF);

        assertNotNull(cpf.toString());
        assertEquals(expectedMaskedCPF, cpf.toString());
    }

    @Test
    public void givenAValidCPF_whenGetValue_thenShouldReturnUnmaskedCPF() {
        final var expectedUnmaskedCPF = "12345678909";
        final var cpf = new CPF(expectedUnmaskedCPF);

        assertNotNull(cpf.getValue());
        assertEquals(expectedUnmaskedCPF, cpf.getValue());
    }

    @Test
    public void givenAMaskedCPF_whenGetValue_thenShouldReturnUnmaskedCPF() {
        final var expectedMaskedCPF = "123.456.789-09";
        final var expectedUnmaskedCPF = "12345678909";
        final var cpf = new CPF(expectedMaskedCPF);

        assertNotNull(cpf.getValue());
        assertEquals(expectedUnmaskedCPF, cpf.getValue());
    }

    @Test
    public void givenANullCPF_whenMaskingIt_thenShouldThrowAnException() {
        final String expectedCPF = null;
        var exception = assertThrows(InvalidCPFException.class, () -> new CPF(expectedCPF).mask());

        assertNotNull(exception);
        final var expectedMessage = "O CPF informado é nulo ou vazio";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenANullCPF_whenUnmaskingIt_thenShouldThrowAnException() {
        final String expectedCPF = null;
        var exception = assertThrows(InvalidCPFException.class, () -> new CPF(expectedCPF).unmask());

        assertNotNull(exception);
        final var expectedMessage = "O CPF informado é nulo ou vazio";
        assertEquals(expectedMessage, exception.getMessage());
    }

}