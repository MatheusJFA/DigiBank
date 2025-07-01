package com.MatheusJFA.Digibank.domain.valueObject;

import com.MatheusJFA.Digibank.shared.exceptions.InvalidCreditCardException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class CreditCardTest {
    /**
     * 1 Cenário: Criar um cartão de crédito com dados válidos.
     * 2 Cenário: Criar um cartão de crédito com número inválido.
     * 3 Cenário: Criar um cartão de crédito com nome do titular inválido.
     * 4 Cenário: Criar um cartão de crédito com data de expiração inválida.
     * 5 Cenário: Criar um cartão de crédito com CVV inválido.
     *
     * 6 Cenário: Criar um cartão de crédito com dados nulos ou vazios.
     * 7 Cenário: Criar um cartão de crédito com número que não passa no algoritmo de Luhn.
     * 8 Cenário: Criar um cartão de crédito com nome do titular contendo mais de 50 caracteres.
     */

    @Test
    public void givenValidData_whenCreatingCreditCard_thenSuccess() {
        final String nextYearDigits = LocalDate.now().plusYears(1).toString().substring(2, 4);
        final String cardNumber = "4111111111111111"; // Valid Visa card number
        final String cardHolderName = "João da Silva";
        final String expirationDate = "12/" + nextYearDigits;
        final String cvv = "123";

        final String expectedCardHolderName = "JOAO DA SILVA";

        CreditCard creditCard = new CreditCard(cardNumber, cardHolderName, expirationDate, cvv);
        assertNotNull(creditCard);
        assertEquals(cardNumber, creditCard.getCardNumber());
        assertEquals(expectedCardHolderName, creditCard.getCardHolderName());
        assertEquals(expirationDate, creditCard.getExpirationDate());
        assertEquals(cvv, creditCard.getCvv());
        assertTrue(creditCard.isValid());
        assertEquals("Visa", creditCard.getCardType());
    }

    @Test
    public void givenInvalidCardNumber_whenCreatingCreditCard_thenThrowsException() {
        String invalidCardNumber = "1234567890123456"; // Invalid card number
        final String nextYearDigits = LocalDate.now().plusYears(1).toString().substring(2, 4);
        final String cardHolderName = "João da Silva";
        final String expirationDate = "12/" + nextYearDigits;
        final String cvv = "123";

        var exception = assertThrows(InvalidCreditCardException.class, () -> {
            new CreditCard(invalidCardNumber, cardHolderName, expirationDate, cvv);
        });

        String expectedMessage = "O número do cartão é inválido";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenInvalidCardHolderName_whenCreatingCreditCard_thenThrowsException() {
        final String nextYearDigits = LocalDate.now().plusYears(1).toString().substring(2, 4);
        final String cardNumber = "4111111111111111";
        String invalidCardHolderName = "João123"; // Invalid card holder name
        final String expirationDate = "12/" + nextYearDigits;
        final String cvv = "123";

        var exception = assertThrows(InvalidCreditCardException.class, () -> {
            new CreditCard(cardNumber, invalidCardHolderName, expirationDate, cvv);
        });

        String expectedMessage = "O nome do titular do cartão deve conter apenas letras, ponto final e espaços";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenInvalidExpirationDate_whenCreatingCreditCard_thenThrowsException() {
        final String nextYearDigits = LocalDate.now().plusYears(1).toString().substring(2, 4);
        final String cardNumber = "4111111111111111";
        final String cardHolderName = "João da Silva";
        String invalidExpirationDate = "13/" + nextYearDigits; // Invalid expiration date
        final String cvv = "123";

        var exception = assertThrows(InvalidCreditCardException.class, () -> {
            new CreditCard(cardNumber, cardHolderName, invalidExpirationDate, cvv);
        });

        String expectedMessage = "A data de expiração do cartão deve estar no formato MM/AA";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenInvalidCvv_whenCreatingCreditCard_thenThrowsException() {
        final String nextYearDigits = LocalDate.now().plusYears(1).toString().substring(2, 4);
        final String cardNumber = "4111111111111111";
        final String cardHolderName = "João da Silva";
        final String expirationDate = "12/" + nextYearDigits;
        String invalidCvv = "12a"; // Invalid CVV

        var exception = assertThrows(InvalidCreditCardException.class, () -> {
            new CreditCard(cardNumber, cardHolderName, expirationDate, invalidCvv);
        });

        String expectedMessage = "O CVV do cartão deve conter exatamente 3 dígitos";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenNullOrEmptyData_whenCreatingCreditCard_thenThrowsException() {
        String nullCardNumber = null;
        final String nextYearDigits = LocalDate.now().plusYears(1).toString().substring(2, 4);
        final String cardHolderName = "João da Silva";
        final String expirationDate = "12/" + nextYearDigits;
        final String cvv = "123";

        var exception = assertThrows(InvalidCreditCardException.class, () -> {
            new CreditCard(nullCardNumber, cardHolderName, expirationDate, cvv);
        });

        String expectedMessage = "O campo número do cartão não pode ser nulo ou vazio";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenCardNumberNotPassingLuhnAlgorithm_whenCreatingCreditCard_thenThrowsException() {
        String invalidCardNumber = "4111111111111112"; // Invalid card number that does not pass Luhn algorithm
        final String nextYearDigits = LocalDate.now().plusYears(1).toString().substring(2, 4);
        final String cardHolderName = "João da Silva";
        final String expirationDate = "12/" + nextYearDigits;
        final String cvv = "123";

        var exception = assertThrows(InvalidCreditCardException.class, () -> {
            new CreditCard(invalidCardNumber, cardHolderName, expirationDate, cvv);
        });

        String expectedMessage = "O número do cartão é inválido";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenCardHolderNameWithMoreThan50Characters_whenCreatingCreditCard_thenThrowsException() {
        final String nextYearDigits = LocalDate.now().plusYears(1).toString().substring(2, 4);
        final String cardNumber = "4111111111111111";
        String longCardHolderName = "João da Silva Campos Salvador e Bourbon";
        final String expirationDate = "12/" + nextYearDigits;
        final String cvv = "123";

        final var creditCard = new CreditCard(cardNumber, longCardHolderName, expirationDate, cvv);

        assertNotNull(creditCard);
        assertEquals(cardNumber, creditCard.getCardNumber());
        String expectedCardHolderName = "JOAO D. S. C. S. E. BOURBON";
        assertEquals(expectedCardHolderName, creditCard.getCardHolderName());
        assertEquals(expirationDate, creditCard.getExpirationDate());
        assertEquals(cvv, creditCard.getCvv());
        String expectedCardType = "Visa";
        assertEquals(expectedCardType, creditCard.getCardType());
        assertTrue(creditCard.isValid());
    }

}