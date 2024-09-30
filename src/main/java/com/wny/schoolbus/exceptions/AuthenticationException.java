package com.wny.schoolbus.exceptions;

import lombok.Getter;
import com.wny.schoolbus.enums.ApiErrorCode;

@Getter
public class AuthenticationException extends RuntimeException {
    private final ApiErrorCode apiErrorCode;

    public AuthenticationException(ApiErrorCode apiErrorCode, String message) {
        super(message);
        this.apiErrorCode = apiErrorCode;
    }
}
