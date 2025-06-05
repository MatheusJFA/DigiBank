package com.matheusjfa.Digibank.domain.exceptions;

public class InvalidCPFException extends RuntimeException {
    public InvalidCPFException(String message) {
        super(message);
    }
}
