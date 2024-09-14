package com.moneyplan.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.ResponseEntity;

public record ErrorResponse<T>(
        String message,
        @JsonInclude(value = JsonInclude.Include.NON_NULL)
        T detail
) {

    public static ResponseEntity<ErrorResponse<Void>> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse<>(errorCode.getMessage(), null));
    }

    public static <T> ResponseEntity<ErrorResponse<T>> toResponseEntity(ErrorCode errorCode, T detail) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new ErrorResponse<>(errorCode.getMessage(), detail));
    }
}