package com.wny.schoolbus.exceptions;

import lombok.Getter;
import com.wny.schoolbus.enums.ApiErrorCode;

@Getter
public class JwtException extends RuntimeException {
    private final ApiErrorCode apiErrorCode;

    public JwtException(ApiErrorCode apiErrorCode, String message) {
        super(message);
        this.apiErrorCode = apiErrorCode;
    }
}
