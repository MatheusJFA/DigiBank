package com.MatheusJFA.Digibank.shared.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class InvalidEmailException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidEmailException(String message) {
        super(message);
    }
}
