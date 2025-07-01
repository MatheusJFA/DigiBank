package com.MatheusJFA.Digibank.shared.exceptions;

import java.io.Serial;
import java.io.Serializable;

public class InvalidFieldException extends RuntimeException implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidFieldException(String message) {
        super(message);
    }
}
