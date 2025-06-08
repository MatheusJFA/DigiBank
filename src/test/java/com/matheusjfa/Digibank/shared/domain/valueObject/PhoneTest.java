package com.matheusjfa.Digibank.shared.domain.valueObject;

import com.matheusjfa.Digibank.shared.domain.exceptions.InvalidPhoneException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {

    @Test
    public void givenAValidPhoneNumberWith8Digits_whenCreatingIt_thenShouldReturnAValidPhone() {
        final String expectedCountryCode = "55";
        final String expectedAreaCode = "11";
        final String expectedPhoneNumber = "9999-9999";

        final var phone = new Phone(expectedCountryCode, expectedAreaCode, expectedPhoneNumber);
        final String unmaskedPhoneNumber = phone.getNumber(); // 9 digits

        assertNotNull(phone);
        assertEquals(expectedCountryCode, phone.getCountryCode());
        assertEquals(expectedAreaCode, phone.getAreaCode());
        assertEquals(unmaskedPhoneNumber, phone.getNumber());
    }

    @Test
    public void givenAValidPhoneNumberWith9Digits_whenCreatingIt_thenShouldReturnAValidPhone() {
        final String expectedCountryCode = "55";
        final String expectedAreaCode = "11";
        final String expectedPhoneNumber = "99999-9999"; // 9 digits

        final var phone = new Phone(expectedCountryCode, expectedAreaCode, expectedPhoneNumber);
        final String unmaskedPhoneNumber = phone.getNumber(); // 9 digits

        assertNotNull(phone);
        assertEquals(expectedCountryCode, phone.getCountryCode());
        assertEquals(expectedAreaCode, phone.getAreaCode());
        assertEquals(unmaskedPhoneNumber, phone.getNumber());
    }

    @Test
    public void givenAnInvalidPhoneNumber_whenCreatingIt_thenShouldThrowAnException() {
        final String invalidCountryCode = "55";
        final String invalidAreaCode = "11";
        final String invalidPhoneNumber = "12345"; // Invalid phone number format

        var exception = assertThrows(InvalidPhoneException.class, () -> new Phone(invalidCountryCode, invalidAreaCode, invalidPhoneNumber));

        assertNotNull(exception);
        final String expectedMessage = "O número de telefone deve ter entre 8 e 9 dígitos";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenAnEmptyPhoneNumber_whenCreatingIt_thenShouldThrowAnException() {
        final String emptyCountryCode = "55";
        final String emptyAreaCode = "11";
        final String emptyPhoneNumber = ""; // Empty phone number

        var exception = assertThrows(InvalidPhoneException.class, () -> new Phone(emptyCountryCode, emptyAreaCode, emptyPhoneNumber));

        assertNotNull(exception);
        final String expectedMessage = "O número de telefone não pode ser nulo ou vazio";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenANullPhoneNumber_whenCreatingIt_thenShouldThrowAnException() {
        final String nullCountryCode = "55";
        final String nullAreaCode = "11";
        final String nullPhoneNumber = null; // Null phone number

        var exception = assertThrows(InvalidPhoneException.class, () -> new Phone(nullCountryCode, nullAreaCode, nullPhoneNumber));

        assertNotNull(exception);
        final String expectedMessage = "O número de telefone não pode ser nulo ou vazio";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void givenAValidPhoneNumberWithSpecialCharacters_whenCreatingIt_thenShouldReturnAValidPhone() {
        final String expectedCountryCode = "55";
        final String expectedAreaCode = "11";
        final String expectedPhoneNumber = "(*@*) 99999-9999"; // Phone number with special characters

        final var phone = new Phone(expectedCountryCode, expectedAreaCode, expectedPhoneNumber);
        final String unmaskedPhoneNumber = phone.getNumber(); // 9 digits

        assertNotNull(phone);
        assertEquals(expectedCountryCode, phone.getCountryCode());
        assertEquals(expectedAreaCode, phone.getAreaCode());
        assertEquals(unmaskedPhoneNumber, phone.getNumber());
    }

    @Test
    public void givenAValidPhoneNumberWithSpaces_whenCreatingIt_thenShouldReturnAValidPhone() {
        final String expectedCountryCode = "55";
        final String expectedAreaCode = "11";
        final String expectedPhoneNumber = "99999 9999"; // Phone number with spaces

        final var phone = new Phone(expectedCountryCode, expectedAreaCode, expectedPhoneNumber);
        final String unmaskedPhoneNumber = phone.getNumber(); // 9 digits

        assertNotNull(phone);
        assertEquals(expectedCountryCode, phone.getCountryCode());
        assertEquals(expectedAreaCode, phone.getAreaCode());
        assertEquals(unmaskedPhoneNumber, phone.getNumber());
    }

    @Test
    public void givenAValidCountryCode_whenCallingGetCountryName_ShouldReturnAValidPhone() {
        final String expectedCountryCode = "55";
        final String expectedAreaCode = "11";
        final String expectedPhoneNumber = "99999 9999";

        final var phone = new Phone(expectedCountryCode, expectedAreaCode, expectedPhoneNumber);

        assertNotNull(phone);
        assertEquals(expectedCountryCode, phone.getCountryCode());
        assertEquals(expectedAreaCode, phone.getAreaCode());
        assertEquals(expectedCountryCode, phone.getCountryCode());
        assertEquals("Brasil", phone.getCountryName());
    }

    @Test
    public void givenAInvalidCountryCode_whenCallingGetCountryName_ShouldReturnAValidPhone() {
        final String expectedCountryCode = "0";
        final String expectedAreaCode = "11";
        final String expectedPhoneNumber = "99999 9999";

        final var phone = new Phone(expectedCountryCode, expectedAreaCode, expectedPhoneNumber);

        assertNotNull(phone);
        assertEquals(expectedCountryCode, phone.getCountryCode());
        assertEquals(expectedAreaCode, phone.getAreaCode());
        assertEquals(expectedCountryCode, phone.getCountryCode());
        assertEquals("País desconhecido", phone.getCountryName());
    }
}