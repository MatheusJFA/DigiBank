package com.matheusjfa.Digibank.shared.valueObjects;

import com.matheusjfa.Digibank.shared.exceptions.InvalidCPFException;
import jakarta.persistence.Embeddable;

@Embeddable
public class CPF {
	private String value;

	public CPF(String value) {
		this.value = validate(value);
	}

	private String validate(String value) {
		if (value == null || value.isEmpty()) {
			throw new InvalidCPFException("O CPF não pode ser nulo ou vazio");
		}

		final var unmaskedValue = value.replaceAll("[^0-9]", "");

		if (unmaskedValue.length() != 11) {
			throw new InvalidCPFException("O CPF informado é inválido");
		}

		if (unmaskedValue.matches("(\\d)\\1{10}")) {
			throw new InvalidCPFException("O CPF informado é inválido");
		}

		checkDigits(unmaskedValue);

		return unmaskedValue;
	}

	public void checkDigits(String value) {
		var sum = 0;

		for (int i = 0; i < 9; i++) {
			sum += Character.getNumericValue(value.charAt(i)) * (10 - i);
		}

		var firstDigit = (sum * 10) % 11;

		if (firstDigit == 10) {
			firstDigit = 0;
		}

		if (Character.getNumericValue(value.charAt(9)) != firstDigit) {
			throw new InvalidCPFException("O CPF informado é inválido");
		}

		sum = 0;

		for (int i = 0; i < 10; i++) {
			sum += Character.getNumericValue(value.charAt(i)) * (11 - i);
		}

		var secondDigit = (sum * 10) % 11;

		if (secondDigit == 10) {
			secondDigit = 0;
		}

		if (Character.getNumericValue(value.charAt(10)) != secondDigit) {
			throw new InvalidCPFException("O CPF informado é inválido");
		}
	}

	public String getValue() {
		return value;
	}

	public String mask() {
		return value.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	}
}
