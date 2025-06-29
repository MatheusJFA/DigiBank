package com.MatheusJFA.Digibank.presentation.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
        String errorCode,
        String message,
        int statusCode,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        Instant timestamp,
        String correlationId,

        Map<String, String>details
) {
    public ErrorResponse(String errorCode, String message, int statusCode, Instant timestamp, String correlationId) {
        this(errorCode, message, statusCode, timestamp, correlationId, null);
    }

}
