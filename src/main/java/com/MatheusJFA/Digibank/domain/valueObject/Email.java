package com.MatheusJFA.Digibank.domain.valueObject;

import com.MatheusJFA.Digibank.shared.exceptions.InvalidEmailException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
public class Email implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Transient
    private final String EMAIL_REGEX = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@"
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String value;

    public Email() {
    }

    public Email(String value) {
        this.value = validate(value);
    }

    private String validate(String value) {
        if (value == null || value.isEmpty()) {
            throw new InvalidEmailException("O Email não pode ser nulo ou vazio");
        }

        if (!value.matches(EMAIL_REGEX)) {
            throw new InvalidEmailException("O Email informado é inválido");
        }

        return value;
    }

    public String getDomain() {
        return value.split("@")[1];
    }
}

