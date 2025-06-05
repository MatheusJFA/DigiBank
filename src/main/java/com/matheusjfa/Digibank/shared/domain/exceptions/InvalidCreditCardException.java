package com.matheusjfa.Digibank.shared.domain.exceptions;

public class InvalidCreditCardException extends RuntimeException {
    public InvalidCreditCardException(String message) {
        super(message);
    }
}
