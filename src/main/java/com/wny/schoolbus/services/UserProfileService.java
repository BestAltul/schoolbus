package com.wny.schoolbus.services;

import com.wny.schoolbus.dtos.UserProfileDTO;

import java.util.List;

public interface UserProfileService {

    List<UserProfileDTO> findAllProfiles();

    UserProfileDTO findProfileById(String id);

    void createProfile(UserProfileDTO userProfile);

    UserProfileDTO updateProfile(UserProfileDTO userProfile);

    void deleteProfileById(String id);

}
