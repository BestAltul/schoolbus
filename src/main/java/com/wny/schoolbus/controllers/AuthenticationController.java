/*
package com.wny.schoolbus.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wny.schoolbus.dtos.AuthenticationDTO;
import com.wny.schoolbus.dtos.TokenResponseDTO;
import com.wny.schoolbus.services.impl.TokenGenerateServiceImpl;

import java.security.NoSuchAlgorithmException;

@RestController
@AllArgsConstructor
@RequestMapping("/services/v3/authentication")
@Tag(name = "Authentication", description = "Authentication API for PASV store")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private TokenGenerateServiceImpl tokenGenerateServiceImpl;

 */
/*   @Operation(summary = "Get User info", description = "Get the login information of currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User information retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Unauthorized - user is not authenticated")
    })
    @GetMapping("/showUserInfo")
    @ResponseBody
    public String showUserInfo(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var userCredentials = (UserCredentialDetails) authentication.getPrincipal();

        return userCredentials.getUserCredentials().getUserLogin();
    }

*//*

    @PostMapping("/login")
    public TokenResponseDTO performLogin(@Valid @RequestBody AuthenticationDTO authenticationDTO) throws NoSuchAlgorithmException {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUserLogin(),authenticationDTO.getUserPassword());
        try{
            authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            return (null);
        }

        return tokenGenerateServiceImpl.GenerateToken(authenticationDTO.getUserLogin());

    }
}
*/
