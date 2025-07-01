package com.MatheusJFA.Digibank.domain.valueObject;

import com.MatheusJFA.Digibank.domain.dictionaries.CountryCodes;
import com.MatheusJFA.Digibank.shared.exceptions.InvalidPhoneException;
import jakarta.persistence.Column;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
public class Phone implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String PHONE_REGEX = "^\\+\\d{2,3}\\s\\(\\d{2}\\)\\s\\d{4,5}-\\d{4}$";
    private final int MIN_LENGTH = 12;
    private final int MAX_LENGTH = 13;

    @Column(name = "phone", nullable = false, unique = true, length = 13)
    private String value;

    public Phone() {
    }

    public Phone(String value) {
        this.value = validate(value);
    }

    private String validate(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidPhoneException("O telefone não pode ser nulo ou vazio.");
        }

        if (!value.matches(PHONE_REGEX)) {
            throw new InvalidPhoneException("O telefone deve estar no formato +DDI (DDD) 9999-9999 ou +DDI (DDD) 99999-9999.");
        }

        final var unmaskedValue  = value.replaceAll("[^\\d]", "");

        if (unmaskedValue.length() < MIN_LENGTH || unmaskedValue.length()  > MAX_LENGTH) {
            throw new InvalidPhoneException("O telefone deve ter entre " +MIN_LENGTH+ " e "+MAX_LENGTH+" dígitos, sem formatação.");
        }

        return unmaskedValue;
    }

    public String mask() {
        return value.replaceAll("(\\d{2})(\\d{2})(\\d{4,5})(\\d{4})", "+$1 ($2) $3-$4");
    }

    public String unmask() {
        return value.replaceAll("[^\\d]", "");
    }

    public String getDDI() {
        return value.substring(0, 2);
    }

    public String getDDD() {
        return value.substring(2, 4);
    }

    public String getNumber() {
        return value.substring(4);
    }

    public String getCountry() {
        final var ddi = Integer.parseInt(getDDI());
        return CountryCodes.getCountryByAreaCode(ddi);
    }
}
