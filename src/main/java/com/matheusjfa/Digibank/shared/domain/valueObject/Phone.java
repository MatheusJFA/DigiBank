package com.matheusjfa.Digibank.shared.domain.valueObject;

import com.matheusjfa.Digibank.shared.domain.dictionaries.CountryCodes;
import com.matheusjfa.Digibank.shared.domain.exceptions.InvalidPhoneException;
import jakarta.persistence.Embeddable;

@Embeddable
public class Phone {
    private final String countryCode;
    private final String areaCode;
    private final String number;

    public Phone(String countryCode, String areaCode, String number) {
        this.countryCode = countryCode;
        this.areaCode = validateAreaCode(areaCode);
        this.number = validateNumber(number);
    }

    public String validateCountryCode(String countryCode) {
        if (countryCode == null || countryCode.isEmpty()) {
            throw new InvalidPhoneException("O código do país não pode ser nulo ou vazio");
        }

        return countryCode;
    }

    public String validateAreaCode(String areaCode) {
        if (areaCode == null || areaCode.isEmpty()) {
            throw new InvalidPhoneException("O código de área não pode ser nulo ou vazio");
        }

        return areaCode;
    }

    public String validateNumber(String number) {
        if (number == null || number.isEmpty()) {
            throw new InvalidPhoneException("O número de telefone não pode ser nulo ou vazio");
        }

        // Remove all non-digit characters from the phone number
        final var cleanedNumber = number.replaceAll("[^\\d]", "");

        final var MIN_LENGTH = 8;
        final var MAX_LENGTH = 9;

        if (cleanedNumber.length() < MIN_LENGTH || cleanedNumber.length() > MAX_LENGTH) {
            throw new InvalidPhoneException("O número de telefone deve ter entre " + MIN_LENGTH + " e " + MAX_LENGTH + " dígitos");
        }

        String onlyNumbers = "\\d+";
        if (!cleanedNumber.matches(onlyNumbers)) {
            throw new InvalidPhoneException("O número de telefone deve conter apenas dígitos");
        }

        return cleanedNumber;
    }

    public void validateNumber() {
        if (number == null || number.isEmpty()) {
            throw new InvalidPhoneException("O número de telefone não pode ser nulo ou vazio");
        }
        if (!number.matches("\\d+")) {
            throw new InvalidPhoneException("O número de telefone deve conter apenas dígitos");
        }
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return CountryCodes.getCountryByAreaCode(Integer.parseInt(countryCode));
    }

    public String getAreaCode() {
        return areaCode;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "+" + getCountryCode() + " (" + getAreaCode() + ") " + getNumber();
    }
}
