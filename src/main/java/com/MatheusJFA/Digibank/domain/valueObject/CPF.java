package com.MatheusJFA.Digibank.domain.valueObject;

import com.MatheusJFA.Digibank.shared.exceptions.InvalidCPFException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
public class CPF implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Transient
    private final static String CPF_SAME_DIGITS_REGEX = "(\\d)\\1{10}";

    @Transient
    private final static String CPF_UNMASK = "[^0-9]";

    @Transient
    private final static String CPF_MASK = "(\\d{3})(\\d{3})(\\d{3})(\\d{2})";

    @Transient
    private final static int CPF_LENGTH = 11;

    @Column(name = "cpf", nullable = false, unique = true, length = CPF_LENGTH)
    private String value;

    public CPF() {
    }

    public CPF(String value) {
        this.value = validate(value);
    }

    private String validate(String value) {
        if (value == null || value.isEmpty()) {
            throw new InvalidCPFException("O CPF não pode ser nulo ou vazio");
        }

        final var unmaskedValue = value.replaceAll(CPF_UNMASK, "");

        if (unmaskedValue.length() != CPF_LENGTH) {
            throw new InvalidCPFException("O CPF informado é inválido");
        }

        if (unmaskedValue.matches(CPF_SAME_DIGITS_REGEX)) {
            throw new InvalidCPFException("O CPF informado é inválido");
        }

        checkDigits(unmaskedValue);

        return unmaskedValue;
    }

    public void checkDigits(String value) {
        var sum = 0;

        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(value.charAt(i)) * (10 - i);
        }

        var firstDigit = (sum * 10) % 11;

        if (firstDigit == 10) {
            firstDigit = 0;
        }

        if (Character.getNumericValue(value.charAt(9)) != firstDigit) {
            throw new InvalidCPFException("O CPF informado é inválido");
        }

        sum = 0;

        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(value.charAt(i)) * (11 - i);
        }

        var secondDigit = (sum * 10) % 11;

        if (secondDigit == 10) {
            secondDigit = 0;
        }

        if (Character.getNumericValue(value.charAt(10)) != secondDigit) {
            throw new InvalidCPFException("O CPF informado é inválido");
        }
    }

    public String mask() {
        return value.replaceAll(CPF_MASK, "$1.$2.$3-$4");
    }
}
