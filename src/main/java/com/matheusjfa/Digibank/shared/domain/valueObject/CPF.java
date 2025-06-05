package com.matheusjfa.Digibank.shared.domain.valueObject;

import com.matheusjfa.Digibank.shared.domain.exceptions.InvalidCPFException;
import jakarta.persistence.Embeddable;

@Embeddable
public class CPF {
    private final String value;

    public CPF(String value) {
        this.value = validate(value);
    }

    private String validate(String value) {
        isNullOrEmpty(value);

        String unmaskedValue = value.replaceAll("[^0-9]", "");
        invalidFormat(unmaskedValue);
        checkDigits(unmaskedValue);

        return unmaskedValue;
    }

    private void isNullOrEmpty(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidCPFException("O CPF informado é nulo ou vazio");
        }
    }

    private void invalidFormat(String value) {
        if (value.length() != 11) {
            throw new InvalidCPFException("O CPF informado não está no padrão esperado");
        }
        if (!value.matches("\\d{11}")) {
            throw new InvalidCPFException("O CPF informado não está no padrão esperado");
        }
    }

    private void checkDigit(String value, int position) {
        int sum = 0;

        for (int i = 0; i < position; i++) {
            sum += Character.getNumericValue(value.charAt(i)) * (position + 1 - i);
        }

        var validationDigit = (sum * 10) % 11;

        if (validationDigit == 10) {
            validationDigit = 0;
        }

        if (validationDigit != Character.getNumericValue(value.charAt(position))) {
            throw new InvalidCPFException("O CPF informado não é válido");
        }
    }

    private void checkDigits(String value) {
        int FIRST_VALIDATION_DIGIT = 9;
        int SECOND_VALIDATION_DIGIT = 10;

        checkDigit(value, FIRST_VALIDATION_DIGIT);
        checkDigit(value, SECOND_VALIDATION_DIGIT);
    }

    public String getValue() {
        return value;
    }

    public String unmask() {
        return this.value.replaceAll("[^0-9]", "");
    }

    public String mask() {
        return this.value.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    @Override
    public String toString() {
        return mask();
    }
}