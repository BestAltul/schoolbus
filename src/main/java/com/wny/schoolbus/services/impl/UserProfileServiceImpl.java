package com.wny.schoolbus.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.wny.schoolbus.dtos.UserProfileDTO;
import com.wny.schoolbus.entities.UserProfile;
import com.wny.schoolbus.repositories.UserProfileRepository;
import com.wny.schoolbus.services.UserProfileService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Override
    public List<UserProfileDTO> findAllProfiles() {
        List<UserProfile> profiles = userProfileRepository.findAll();

        // Map UserProfile entities to UserProfileDTOs directly
        return profiles.stream()
                .map(profile -> new UserProfileDTO(
                        profile.getId(),
                        profile.getFirstName(),
                        profile.getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public UserProfileDTO findProfileById(String id) {
        UserProfile profile = userProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User profile not found with ID: " + id));
        return new UserProfileDTO(
                profile.getId(),
                profile.getFirstName(),
                profile.getLastName());

    }

    @Override
    public void createProfile(UserProfileDTO userProfileDTO) {
        var userProfile = new UserProfile();
        userProfile.setId(userProfileDTO.getId());
        userProfile.setFirstName(userProfileDTO.getFirstName());
        userProfile.setLastName(userProfileDTO.getLastName());
        userProfileRepository.save(userProfile);
    }

    @Override
    public UserProfileDTO updateProfile(UserProfileDTO userProfileDTO) {
        // Find the existing profile
        UserProfile existingProfile = userProfileRepository.findById(userProfileDTO.getId())
                .orElseThrow(() -> new RuntimeException("User Profile not found"));

        // Update the fields manually from the DTO
        existingProfile.setFirstName(userProfileDTO.getFirstName());
        existingProfile.setLastName(userProfileDTO.getLastName());

        // Save the updated profile
        UserProfile updatedProfile = userProfileRepository.save(existingProfile);

        // Return the updated profile as a DTO
        return new UserProfileDTO(
                updatedProfile.getId(),
                updatedProfile.getFirstName(),
                updatedProfile.getLastName());
    }

    @Override
    public void deleteProfileById(String id) {
        if (userProfileRepository.existsById(id)) {
            userProfileRepository.deleteById(id);
        } else {
            throw new RuntimeException("User Profile not found");
        }
    }
}
