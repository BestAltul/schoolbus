package com.wny.schoolbus.services;

import jakarta.validation.Valid;
import com.wny.schoolbus.dtos.UserCredentialsDTO;
import com.wny.schoolbus.entities.UserCredentials;
import com.wny.schoolbus.entities.UserProfile;

import java.security.NoSuchAlgorithmException;

public interface UserCredentialsService {
    public UserCredentials createUserCredentials(@Valid UserCredentialsDTO userCredentialsDTO) throws NoSuchAlgorithmException;
    public UserProfile getUserByEmail(String email);
}
