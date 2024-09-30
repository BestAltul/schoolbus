package com.wny.schoolbus.services.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.wny.schoolbus.entities.UserCredentials;
import com.wny.schoolbus.repositories.UserCredentialsRepository;
import com.wny.schoolbus.security.UserCredentialDetails;
import com.wny.schoolbus.services.UserCredentialsDetailService;

import java.util.Optional;

@Service
@Setter
@Getter
@AllArgsConstructor
public class UserCredentialsDetailServiceImpl implements UserCredentialsDetailService {

    private final UserCredentialsRepository userCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException {

        Optional<UserCredentials> userCredentials = userCredentialsRepository.findByUserLogin(userLogin);

        if(userCredentials.isEmpty()){
            throw new UsernameNotFoundException("User not found! "+userLogin);
        }

        return new UserCredentialDetails(userCredentials.get());
    }
}
