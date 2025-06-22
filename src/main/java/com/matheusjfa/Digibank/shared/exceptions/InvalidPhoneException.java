package com.matheusjfa.Digibank.shared.exceptions;

import java.io.Serial;

public class InvalidPhoneException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	public InvalidPhoneException(String message) {
		super(message);
	}
}
