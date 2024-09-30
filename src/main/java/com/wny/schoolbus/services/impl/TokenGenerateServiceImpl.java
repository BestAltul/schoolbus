package com.wny.schoolbus.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.wny.schoolbus.dtos.TokenResponseDTO;
import com.wny.schoolbus.dtos.UserCredentialsDTO;
import com.wny.schoolbus.entities.UserCredentials;
import com.wny.schoolbus.services.TokenGenerateService;
import com.wny.schoolbus.services.UserCredentialsService;
import com.wny.schoolbus.utils.JWTUtil;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class TokenGenerateServiceImpl implements TokenGenerateService {
    private UserCredentialsService userCredentialsService;
    private JWTUtil jwtUtil;

    public TokenResponseDTO  GenerateToken(String userLogin) throws NoSuchAlgorithmException {

        String token = jwtUtil.generateToken(userLogin);

        return new TokenResponseDTO(token);
    }

    public TokenResponseDTO  CreateUserCredentialsAndGenerateToken(UserCredentialsDTO userCredentialsDTO) throws NoSuchAlgorithmException {

        UserCredentials userCredentials = userCredentialsService.createUserCredentials(userCredentialsDTO);

        String token = jwtUtil.generateToken(userCredentials.getUserLogin());

        return new TokenResponseDTO(token);
    }
}
