package com.wny.schoolbus.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserCredentialsDetailService extends UserDetailsService{

    public UserDetails loadUserByUsername(String userLogin) throws UsernameNotFoundException;

}
