package com.matheusjfa.Digibank.shared.exceptions;

import java.io.Serial;

public class InvalidCPFException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1L;

	public InvalidCPFException(String message) {
				super(message);
		}
}

