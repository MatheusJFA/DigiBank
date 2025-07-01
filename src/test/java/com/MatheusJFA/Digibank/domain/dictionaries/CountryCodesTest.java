package com.MatheusJFA.Digibank.domain.dictionaries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CountryCodesTest {
    @Test
    public void givenAValidCountryCode_whenGetCountryName_thenReturnCorrectName() {
        String expectedCountryName = "Brasil";
        Integer expectedCountryAreaCode = 55;

        String countryName = CountryCodes.getCountryByAreaCode(expectedCountryAreaCode);

        assertNotNull(countryName);
        assertEquals(expectedCountryName, countryName);
    }

    @Test
    public void givenAnInvalidCountryCode_whenGetCountryName_thenReturnNull() {
        Integer invalidCountryAreaCode = 999;

        String countryName = CountryCodes.getCountryByAreaCode(invalidCountryAreaCode);

        String expectedCountryName = "País desconhecido";
        assertEquals(expectedCountryName, countryName);
    }

    @Test
    public void givenAValidCountryName_whenGetCountryAreaCode_thenReturnCorrectAreaCode() {
        String expectedCountryName = "Brasil";
        Integer expectedCountryAreaCode = 55;

        Integer countryAreaCode = CountryCodes.getAreaCodeByCountryName(expectedCountryName);

        assertNotNull(countryAreaCode);
        assertEquals(expectedCountryAreaCode, countryAreaCode);
    }

    @Test
    public void givenAnInvalidCountryName_whenGetCountryAreaCode_thenReturnNull() {
        String invalidCountryName = "InvalidCountry";

        final var exception = assertThrows(IllegalArgumentException.class, () -> {
            CountryCodes.getAreaCodeByCountryName(invalidCountryName);
        });

        assertNotNull(exception);
        final String expectedMessage = "Código de área não encontrado para o país: " + invalidCountryName;
        assertEquals(expectedMessage, exception.getMessage());
    }

}