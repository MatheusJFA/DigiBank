package com.MatheusJFA.Digibank.domain.valueObject;


import com.MatheusJFA.Digibank.shared.exceptions.InvalidCreditCardException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Locale;

@Embeddable
public class CreditCard implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "card_number", nullable = false, length = 19)
    private String cardNumber;

    @Column(name = "card_holder_name", nullable = false, length = 30)
    private String cardHolderName;

    @Column(name = "expiration_date", nullable = false, length = 5)
    private String expirationDate;

    @Column(name = "cvv", nullable = false, length = 3)
    private String cvv;

    public CreditCard() {
    }

    public CreditCard(String cardNumber, String cardHolderName, String expirationDate, String cvv) {
        this.cardNumber = validateCardNumber(cardNumber);
        this.cardHolderName = validateCardHolderName(cardHolderName);
        this.expirationDate = validateExpirationDate(expirationDate);
        this.cvv = validateCvv(cvv);
    }

    private void isNullOrEmpty(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            throw new InvalidCreditCardException("O campo " + fieldName + " não pode ser nulo ou vazio");
        }
    }

    private void invalidLength(String value, String fieldName, int expectedMinimalLength) {
        if (value.length() != expectedMinimalLength) {
            throw new InvalidCreditCardException("O campo " + fieldName + " deve ter exatamente " + expectedMinimalLength + " caracteres");
        }
    }

    private void invalidLengthRange(String value, String fieldName, int minLength, int maxLength) {
        if (value.length() < minLength || value.length() > maxLength) {
            throw new InvalidCreditCardException("O campo " + fieldName + " deve ter entre " + minLength + " e " + maxLength + " caracteres");
        }
    }

    private void invalidCvv(String cvv) {
        if (!cvv.matches("\\d{3}")) {
            throw new InvalidCreditCardException("O CVV do cartão deve conter exatamente 3 dígitos");
        }
    }

    private void invalidExpirationDate(String expirationDate) {
        if (!expirationDate.matches("(0[1-9]|1[0-2])/[0-9]{2}")) {
            throw new InvalidCreditCardException("A data de expiração do cartão deve estar no formato MM/AA");
        }

        String[] parts = expirationDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]) + 2000; // Assuming year is in the format 'YY'

        final int JANUARY = 1;
        final int DECEMBER = 12;

        if (month < JANUARY || month > DECEMBER) {
            throw new InvalidCreditCardException("O mês da data de expiração deve ser entre 01 e 12");
        }

        int thisYear = LocalDate.now().getYear();
        if (year < thisYear) {
            throw new InvalidCreditCardException("A data de expiração do cartão já passou");
        }
    }

    private void LuhnAlgorithm(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        for (int index = cardNumber.length() - 1; index >= 0; index--) {
            int number = Integer.parseInt(cardNumber.substring(index, index + 1));
            if (alternate) {
                number *= 2;
                if (number > 9) number -= 9;
            }
            sum += number;
            alternate = !alternate;
        }

        if (sum % 10 != 0) {
            throw new InvalidCreditCardException("O número do cartão é inválido");
        }
    }

    private void invalidCardHolderName(String cardHolderName) {
        if (!cardHolderName.matches("[a-zA-Z.\\s]+")) {
            throw new InvalidCreditCardException("O nome do titular do cartão deve conter apenas letras, ponto final e espaços");
        }
    }

    private String normalizedCardHolderName(String cardHolderName, int maxLength) {
        if (cardHolderName == null || cardHolderName.isBlank()) return "";

        String normalized = StringUtils.stripAccents(cardHolderName).trim();
        String[] names = normalized.split("\\s+");
        if (names.length == 0) return "";

        StringBuilder result = new StringBuilder();
        String firstName = names[0];
        String lastName = names.length > 1 ? names[names.length - 1] : "";

        result.append(firstName);

        // Adiciona as iniciais dos nomes do meio, se existirem
        if(cardHolderName.length() > maxLength) {
            for (int i = 1; i < names.length - 1; i++) {
                if (!names[i].isEmpty()) {
                    result.append(" ").append(names[i].charAt(0)).append('.');
                }
            }
        } else {
            for (int i = 1; i < names.length - 1; i++) {
                if (!names[i].isEmpty()) {
                    result.append(" ").append(names[i]);
                }
            }
        }

        if (!lastName.isEmpty() && names.length > 1) {
            result.append(" ").append(lastName);
        }

        String finalResult = result.toString().toUpperCase(Locale.ROOT).trim();

        // Truncate if necessary
        if (finalResult.length() > maxLength) {
            finalResult = finalResult.substring(0, maxLength).trim();
        }

        return finalResult;
    }

    public String validateCardNumber(String cardNumber) {
        isNullOrEmpty(cardNumber, "número do cartão");
        int cardMinimalLength = 12;
        int cardMaximalLength = 19;
        invalidLengthRange(cardNumber, "número do cartão", cardMinimalLength, cardMaximalLength);
        LuhnAlgorithm(cardNumber);

        return cardNumber;
    }

    private String validateCardHolderName(String cardHolderName) {
        isNullOrEmpty(cardHolderName, "nome do titular do cartão");
        String normalizedCardHolderName = normalizedCardHolderName(cardHolderName, 30);

        int minLength = 3;
        int maxLength = 30;

        invalidLengthRange(normalizedCardHolderName, "nome do titular do cartão", minLength, maxLength);
        invalidCardHolderName(normalizedCardHolderName);
        return normalizedCardHolderName;
    }

    private String validateExpirationDate(String expirationDate) {
        isNullOrEmpty(expirationDate, "data de expiração do cartão");
        invalidLength(expirationDate, "data de expiração do cartão", 5);
        invalidExpirationDate(expirationDate);
        return expirationDate;
    }

    private String validateCvv(String cvv) {
        isNullOrEmpty(cvv, "CVV do cartão");
        invalidCvv(cvv);
        return cvv;
    }

    public boolean isValid() {
        try {
            validateCardNumber(cardNumber);
            validateCardHolderName(cardHolderName);
            validateExpirationDate(expirationDate);
            validateCvv(cvv);
            return true;
        } catch (InvalidCreditCardException e) {
            return false;
        }
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public String getCardType() {
        String number = cardNumber.replaceAll("\\s", "");
        if (number.matches("^3[47][0-9]{13}$")) return "American Express";
        if (number.matches("^3(?:0[0-5]|[68][0-9])[0-9]{11}$")) return "Diners";
        if (number.matches("^6011[0-9]{12}$") || number.matches("^65[0-9]{14}$") || number.matches("^64[4-9][0-9]{13}$")) return "Discover";
        if (number.matches("^35(2[89]|[3-8][0-9])[0-9]{12}$")) return "JCB";
        if (number.matches("^(5[1-5][0-9]{14}|2(2[2-9][0-9]{12}|[3-6][0-9]{13}|7[01][0-9]{12}|720[0-9]{12}))$")) return "Mastercard";
        if (number.matches("^4[0-9]{12}(?:[0-9]{3})?(?:[0-9]{3})?$")) return "Visa";
        if (number.matches("^62[0-9]{14,17}$")) return "China UnionPay";
        if (number.matches("^4360[0-9]{12}$")) return "Cartes Bancaires";
        if (number.matches("^4035[0-9]{12}$")) return "Cartes Bancaires / Visa Debit";
        if (number.matches("^4871[0-9]{12}$")) return "Bancontact / Visa";
        if (number.matches("^6703[0-9]{12}$")) return "Bancontact / Maestro";
        if (number.matches("^4166[0-9]{12}$")) return "Visa Classic";
        if (number.matches("^5454[0-9]{12}$")) return "Mastercard";
        if (number.matches("^2222[0-9]{12}$")) return "Mastercard Credit";
        return "Desconhecido";
    }
}
