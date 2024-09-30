package com.wny.schoolbus.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.wny.schoolbus.enums.ApiErrorCode;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError handleInvalidParameterException(AuthenticationException exception) {
        log.error(exception.getMessage());

        return ApiError.builder()
                .message(exception.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .code(exception.getApiErrorCode())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ApiError handleInternalServerError(Exception exception) {
        log.error("Internal server error", exception);

        return ApiError.builder()
                .message("Something went wrong!")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(ApiErrorCode.INTERNAL_SERVER_ERROR)
                .build();
    }
}
