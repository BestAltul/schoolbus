package com.wny.schoolbus.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.wny.schoolbus.enums.ApiErrorCode;
import com.wny.schoolbus.exceptions.ApiError;

import java.io.Serializable;

@RequiredArgsConstructor
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint, Serializable {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        var outputStream = response.getOutputStream();

        objectMapper.writeValue(outputStream, ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .code(ApiErrorCode.AUTHENTICATION_ERROR)
                .message(authException.getMessage())
                .build());

        outputStream.flush();
    }
}