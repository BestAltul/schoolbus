package com.wny.schoolbus.services.impl;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.wny.schoolbus.dtos.UserCredentialsDTO;
import com.wny.schoolbus.entities.UserCredentials;
import com.wny.schoolbus.entities.UserProfile;
import com.wny.schoolbus.repositories.UserCredentialsRepository;
import com.wny.schoolbus.repositories.UserProfileRepository;
import com.wny.schoolbus.services.UserCredentialsService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserCredentialsServiceImpl implements UserCredentialsService {

    private final UserCredentialsRepository userCredentialsRepository;
    private final ModelMapper modelMapper;
    private final UserProfileRepository userProfileRepository;

    @Override
    public UserCredentials createUserCredentials(@Valid UserCredentialsDTO userCredentialsDTO) throws NoSuchAlgorithmException {

        var salt = generateSalt();

        var userCredentialsBuilt = UserCredentials.builder()
                .userLogin(userCredentialsDTO.getUserLogin())
                .userProfile(getUserByEmail(userCredentialsDTO.getEmail()))
                .salt(salt)
                .hashedPassword(hashPassword(userCredentialsDTO.getUserPassword(), salt))
                .build();

        userCredentialsRepository.save(userCredentialsBuilt);

        return userCredentialsBuilt;
    }

    @Override
    public UserProfile getUserByEmail(String email) {

        Optional<UserProfile> userProfile =  userProfileRepository.findByEmail(email);

        return userProfile.orElse(null);
    }

    public static String generateSalt(){

        SecureRandom random = new SecureRandom();

        byte[] salt = new byte[16];

        random.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);

    }

    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {

        String saltedPassword = salt+password;

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] hashedPassword = md.digest(saltedPassword.getBytes());

        return Base64.getEncoder().encodeToString(hashedPassword);
    }
}
