package com.wny.schoolbus.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.wny.schoolbus.entities.UserCredentials;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class UserCredentialDetails implements UserDetails {

    private UserCredentials userCredentials;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword(){

        return this.userCredentials.getHashedPassword();
    }

    @Override
    public String getUsername() {

        return this.userCredentials.getUserLogin();
    }
}
