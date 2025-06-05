package com.matheusjfa.Digibank.shared.domain.exceptions;

public class InvalidCPFException extends RuntimeException {
    public InvalidCPFException(String message) {
        super(message);
    }
}
