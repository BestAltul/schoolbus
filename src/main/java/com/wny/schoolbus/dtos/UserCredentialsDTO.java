package com.wny.schoolbus.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialsDTO {
    @NotEmpty(message = "Login can't be empty")
    @Size(min=2, max=16, message = "Login should be between 2 and 16 symbols")
    private String userLogin;
    private String userPassword;
    private String email;
}
