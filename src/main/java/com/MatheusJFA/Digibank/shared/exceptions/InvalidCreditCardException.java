package com.MatheusJFA.Digibank.shared.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class InvalidCreditCardException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidCreditCardException(String message) {
        super(message);
    }
}
