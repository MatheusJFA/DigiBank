package com.matheusjfa.Digibank.shared.domain.valueObject;

import com.matheusjfa.Digibank.shared.domain.exceptions.InvalidEmailException;
import jakarta.persistence.Embeddable;

@Embeddable
public class Email {
    private final String value;

    private final String EMAIL_REGEX =
            "^[A-Za-z0-9._%+-]+@" +
                    "(?!-)[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*" +
                    "(?<!-)\\.[A-Za-z]{2,}$";

    public Email(String value) {
        this.value = validate(value);
    }

    private String validate(String value) {
        isNullOrEmpty(value);
        invalidFormat(value);

        return value;
    }

    public String getValue() {
        return value;
    }

    private void invalidFormat(String value) throws InvalidEmailException {
        if (!value.matches(EMAIL_REGEX)) {
            throw new InvalidEmailException("O email informado é inválido");
        }
    }

    private void isNullOrEmpty(String value) throws InvalidEmailException {
        if (value == null || value.isEmpty()) {
            throw new InvalidEmailException("O email informado é nulo ou vazio");
        }
    }

    public String getDomain() {
        return value.substring(value.indexOf('@') + 1);
    }

    @Override
    public String toString() {
        return value;
    }

}
