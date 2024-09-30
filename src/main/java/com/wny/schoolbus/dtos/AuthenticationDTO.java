package com.wny.schoolbus.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationDTO {

    @NotEmpty(message = "Username can't be empty")
    @Size(min=2,max = 100,message = "Username must be at least 2 and max 100 characters")
    private String userLogin;

    private String userPassword;

}
