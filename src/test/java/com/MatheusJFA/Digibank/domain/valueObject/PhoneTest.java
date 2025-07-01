package com.MatheusJFA.Digibank.domain.valueObject;

import com.MatheusJFA.Digibank.shared.exceptions.InvalidPhoneException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneTest {

    /***
     * Cenários de teste:
     * 1. Dado um número de telefone com formatação (ex: "+55 (11) 91234-5678"), quando criado, então deve ser válido.
     * 2. Dado um telefone nulo ou vazio, quando criado, então deve lançar uma exceção InvalidPhoneException.
     * 3. Dado um telefone com caracteres não numéricos, quando criado, então deve lançar uma exceção InvalidPhoneException.
     * 4. Dado um telefone com tamanho diferente de 12 a 13 caracteres, quando criado, então deve lançar uma exceção InvalidPhoneException.
     * 5. Dado um telefone com formatação inválida (ex: "1234567890"), quando tentar criá-lo, então deve lançar uma exceção InvalidPhoneException.
     * 6. Dado um telefone válido, quando chamado o método mask, então deve retornar o telefone com a formatação correta (ex: "+55 (11) 91234-5678").
     * 7. Dado um telefone válido, quando chamado o método unmask, então deve retornar o telefone sem formatação (ex: "5511912345678").
     * 8. Dado um telefone válido, quando chamado o método getDDI e getDDD, então deve retornar os valores corretos (ex: DDI = "55", DDD = "11") e falar que o país é Brasil.
     */

    @ParameterizedTest
    @CsvSource({
            "+55 (31) 99999-9999, 5531999999999", // Número válido de 9 digitos com formatação
            "+55 (31) 9999-9999, 553199999999", // Número válido de 8 digitos com formatação
    })
    public void givenAValidPhone_whenCreated_thenShouldBeValid(final String input, final String expectedValue) {
        // Arrange & Act
        Phone phone = new Phone(input);

        // Assert
        assertNotNull(phone);
        assertEquals(expectedValue, phone.getValue());
    }

    @ParameterizedTest
    @NullAndEmptySource
    public void givenANullOrEmptyPhone_whenCreated_thenShouldThrowInvalidPhoneException(String phone) {
        // Arrange & Act
        InvalidPhoneException exception = assertThrows(InvalidPhoneException.class, () -> new Phone(phone));

        // Assert
        assertNotNull(exception);
        String expectedMessage = "O telefone não pode ser nulo ou vazio.";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "+55 (31) 9999-999, O telefone deve estar no formato +DDI (DDD) 9999-9999 ou +DDI (DDD) 99999-9999.", // 11 dígitos
            "+55 (31) 99999-99999, O telefone deve estar no formato +DDI (DDD) 9999-9999 ou +DDI (DDD) 99999-9999.", // 14 dígitos
            "+55 (31) 9999-999a, O telefone deve estar no formato +DDI (DDD) 9999-9999 ou +DDI (DDD) 99999-9999.", // Caracteres não numéricos
            "1234567890, O telefone deve estar no formato +DDI (DDD) 9999-9999 ou +DDI (DDD) 99999-9999." // Formato inválido
    })
    public void givenAnInvalidPhone_whenCreated_thenShouldThrowInvalidPhoneException(final String input, final String expectedMessage) {
        // Arrange & Act
        InvalidPhoneException exception = assertThrows(InvalidPhoneException.class, () -> new Phone(input));

        // Assert
        assertNotNull(exception);
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenAValidPhone_whenMasked_thenShouldReturnFormattedPhone() {
        // Arrange
        String input = "+55 (31) 98765-4321";
        Phone phone = new Phone(input);

        // Act
        String maskedPhone = phone.mask();

        // Assert
        assertEquals(input, maskedPhone);
    }

    @Test
    public void givenAValidPhone_whenUnmasked_thenShouldReturnUnformattedPhone() {
        // Arrange
        String input = "+55 (31) 98765-4321";
        Phone phone = new Phone(input);

        // Act
        String unmaskedPhone = phone.unmask();

        // Assert
        assertEquals("5531987654321", unmaskedPhone);
        assertEquals(phone.getValue(), unmaskedPhone); // Verifica se o valor interno é sem formatação
    }

    @Test
    public void givenAValidPhone_whenGetDDIAndDDD_thenShouldReturnCorrectValues() {
        // Arrange
        String input = "+55 (31) 98765-4321";
        Phone phone = new Phone(input);

        // Act
        var ddi = phone.getDDI();
        var ddd = phone.getDDD();
        var phoneNumber = "987654321";

        // Assert
        assertEquals("55", ddi);
        assertEquals("31", ddd);
        assertEquals(phoneNumber, phone.getNumber()); // Verifica se o número está correto
    }

    @Test
    public void givenAValidPhone_whenGetCountry_thenShouldReturnCorrectCountry() {
        // Arrange
        String input = "+55 (31) 98765-4321";
        Phone phone = new Phone(input);

        // Act
        String country = phone.getCountry();

        // Assert
        String expectedCountry = "Brasil";
        assertEquals(expectedCountry, country);
    }

}