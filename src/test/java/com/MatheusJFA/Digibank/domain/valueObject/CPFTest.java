package com.MatheusJFA.Digibank.domain.valueObject;

import com.MatheusJFA.Digibank.shared.exceptions.InvalidCPFException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

public class CPFTest {

    /***
     * Cenários de teste:
     * 1. Dado um CPF válido, quando criado, então deve ser válido.
     * 1.1 Dado um CPF válido com formatação (ex: "123.456.789-09"), quando criado, então deve ser válido.
     * 1.2 Dado um CPF válido com zeros à esquerda (ex: "00012345678"), quando criado, então deve ser válido.
     * 1.3 Dado um CPF com espacos em branco (ex: " 12345678909 " ou "123 456 789 09"), quando criado, então deve ser válido.
     * 1.4 Dado um CPF com caracteres especiais, porém válido (ex: "123*456$789@09"), quando criado, então deve ser válido.
     * 2. Dado um CPF inválido, quando criado, então deve lançar uma exceção InvalidCPFException.
     * 3. Dado um CPF nulo ou vazio, quando criado, então deve lançar uma exceção InvalidCPFException.
     * 4. Dado um CPF com caracteres não numéricos, quando criado, então deve lançar uma exceção InvalidCPFException.
     * 5. Dado um CPF com tamanho diferente de 11 caracteres, quando criado, então deve lançar uma exceção InvalidCPFException.
     * 6. Dado um CPF com caracteres repetidos (ex: "11111111111"), quando criado, então deve lançar uma exceção InvalidCPFException.
     * 7. Dado um CPF com dígitos verificadores inválidos, quando criado, então deve lançar uma exceção InvalidCPFException.
     * 8. Dado um CPF com caracteres especiais, porém inválido, quando criado, então deve lançar uma exceção InvalidCPFException.
     * 9. Dado um CPF válido ao chamar o método `mask()`, então deve retornar o CPF formatado.
     * * Exemplos de CPF válidos:
     *  - "12345678909"
     *  - "123.456.789-09"
     */

    @Test
    public void givenAValidCPF_whenCreated_thenShouldBeValid() {
        // Arrange
        String validCPF = "12345678909"; // Exemplo de CPF válido

        // Act
        CPF cpf = new CPF(validCPF);

        // Assert
        assertNotNull(cpf);
        assertEquals(validCPF, cpf.getValue());
    }

    @Test
    public void givenAValidFormattedCPF_whenCreated_thenShouldBeValid() {
        // Arrange
        String validFormattedCPF = "123.456.789-09"; // Exemplo de CPF válido formatado

        // Act
        CPF cpf = new CPF(validFormattedCPF); // Remove formatação
        String unformattedCPF = validFormattedCPF.replaceAll("[^0-9]", "");

        // Assert
        assertNotNull(cpf);
        assertEquals(unformattedCPF, cpf.getValue());
    }

    @Test
    public void givenACPFWithLeadingZeros_whenCreated_thenShouldBeValid() {
        // Arrange
        String cpfWithLeadingZeros = "02650396067"; // Exemplo de CPF com zero à esquerda

        // Act
        CPF cpf = new CPF(cpfWithLeadingZeros);

        // Assert
        assertNotNull(cpf);
        assertEquals(cpfWithLeadingZeros, cpf.getValue());
    }

    @ParameterizedTest
    @CsvSource({
            "' 12345678909 '",
            "'123 456 789 09'",
    })
    public void givenACPFWithSpaces_whenCreated_thenShouldBeValid(String cpfWithSpaces) {
        // Act
        CPF cpf = new CPF(cpfWithSpaces); // Remove espaços

        // Assert
        assertNotNull(cpf);
        assertEquals("12345678909", cpf.getValue());
    }

    @Test
    public void givenACPFWithSpecialCharacters_whenCreated_thenShouldBeValid() {
        // Arrange
        String cpfWithSpecialCharacters = "123*456$789@09"; // Exemplo de CPF com caracteres especiais

        // Act
        var CPF = new CPF(cpfWithSpecialCharacters);

        // Assert
        assertNotNull(CPF);
        assertEquals("12345678909", CPF.getValue()); // Verifica se os caracteres especiais foram removidos
    }

    @Test
    public void givenAnInvalidCPF_whenCreated_thenShouldThrowInvalidCPFException() {
        // Arrange
        String invalidCPF = "12345678901"; // Exemplo de CPF inválido (dígitos verificadores incorretos)

        // Act
        InvalidCPFException exception = assertThrows(InvalidCPFException.class, () -> {
            new CPF(invalidCPF);
        });

        // Assert
        String expectedMessage = "O CPF informado é inválido";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest(name = "{index} - CPF: {0}")
    @NullAndEmptySource
    public void givenANullOrEmptyCPF_whenCreated_thenShouldThrowInvalidCPFException(String cpf) {
        // Act
        InvalidCPFException exception = assertThrows(InvalidCPFException.class, () -> {
            new CPF(cpf);
        });

        // Assert
        String expectedMessage = "O CPF não pode ser nulo ou vazio";
        assertEquals(expectedMessage, exception.getMessage());

    }

    @Test
    public void givenACPFWithNonNumericCharacters_whenCreated_thenShouldThrowInvalidCPFException() {
        // Arrange
        String cpfWithNonNumeric = "123.456.789-0A"; // Exemplo de CPF com caracteres não numéricos

        // Act
        InvalidCPFException exception = assertThrows(InvalidCPFException.class, () -> {
            new CPF(cpfWithNonNumeric);
        });

        // Assert
        String expectedMessage = "O CPF informado é inválido";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenACPFWithInvalidLength_whenCreated_thenShouldThrowInvalidCPFException() {
        // Arrange
        String invalidLengthCPF = "123456789"; // Exemplo de CPF com tamanho diferente de 11 caracteres

        // Act
        InvalidCPFException exception = assertThrows(InvalidCPFException.class, () -> {
            new CPF(invalidLengthCPF);
        });

        // Assert
        String expectedMessage = "O CPF informado é inválido";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenACPFWithRepeatedCharacters_whenCreated_thenShouldThrowInvalidCPFException() {
        // Arrange
        String repeatedCharactersCPF = "11111111111"; // Exemplo de CPF com caracteres repetidos

        // Act
        InvalidCPFException exception = assertThrows(InvalidCPFException.class, () -> {
            new CPF(repeatedCharactersCPF);
        });

        // Assert
        String expectedMessage = "O CPF informado é inválido";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenACPFWithInvalidCheckDigits_whenCreated_thenShouldThrowInvalidCPFException() {
        // Arrange
        String invalidCheckDigitsCPF = "12345678900"; // Exemplo de CPF com dígitos verificadores inválidos

        // Act
        InvalidCPFException exception = assertThrows(InvalidCPFException.class, () -> {
            new CPF(invalidCheckDigitsCPF);
        });

        // Assert
        String expectedMessage = "O CPF informado é inválido";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenACPFWithSpecialCharacters_whenCreated_thenShouldThrowInvalidCPFException() {
        // Arrange
        String invalidSpecialCharactersCPF = "123@456$789-00"; // Exemplo de CPF com caracteres especiais inválidos

        // Act
        InvalidCPFException exception = assertThrows(InvalidCPFException.class, () -> {
            new CPF(invalidSpecialCharactersCPF);
        });

        // Assert
        String expectedMessage = "O CPF informado é inválido";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenAValidCPF_whenMasked_thenShouldReturnFormattedCPF() {
        // Arrange
        String validCPF = "12345678909"; // Exemplo de CPF válido
        CPF cpf = new CPF(validCPF);

        // Act
        String maskedCPF = cpf.mask();

        // Assert
        String expectedMaskedCPF = "123.456.789-09";
        assertEquals(expectedMaskedCPF, maskedCPF); // Verifica se o CPF foi formatado corretamente
    }

}