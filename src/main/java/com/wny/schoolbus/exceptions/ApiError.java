package com.wny.schoolbus.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;
import com.wny.schoolbus.enums.ApiErrorCode;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError implements Serializable {
    private HttpStatus status;
    private String message;
    private ApiErrorCode code;
}
