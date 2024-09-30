/*
package com.wny.schoolbus.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wny.schoolbus.dtos.TokenResponseDTO;
import com.wny.schoolbus.dtos.UserCredentialsDTO;
import com.wny.schoolbus.services.impl.TokenGenerateServiceImpl;

import java.security.NoSuchAlgorithmException;

*/
/**
 * Created by Artem Peshko
 * E-Mail artem.peshko@gmail.com
 * pasv-store
 * Когда я начинал это писать, только я и Бог знали, как это работает.
 * Сейчас знает только Бог.
 *//*


@RestController
@AllArgsConstructor
@RequestMapping("services/v3/registration")
@Tag(name = "Registration", description = "Registration API for PASV store")
public class RegistrationController {

    private TokenGenerateServiceImpl tokenGenerateServiceImpl;

    @Operation(summary = "User registration", description = "Register a new user and receive a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully and JWT token returned.",
                    content = @Content(mediaType = "application,json",schema = @Schema(implementation = TokenResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = "application,json")),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/registration")
    public TokenResponseDTO performUserCredentialsRegistration(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO,
                                                                               BindingResult bindingResult) throws NoSuchAlgorithmException {
        if (bindingResult.hasErrors()) {
            return null;
        }

        return tokenGenerateServiceImpl.CreateUserCredentialsAndGenerateToken(userCredentialsDTO);

    }
}
*/
