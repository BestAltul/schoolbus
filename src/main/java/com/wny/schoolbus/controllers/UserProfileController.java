
package com.wny.schoolbus.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wny.schoolbus.dtos.UserProfileDTO;
import com.wny.schoolbus.exceptions.ApiError;
import com.wny.schoolbus.services.UserProfileService;

import java.util.List;

@Tag(name = "User controller V3")
@ApiResponses({
        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(name = "Bad Request", implementation = ApiError.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
        @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(name = "Unauthorized", implementation = ApiError.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
        @ApiResponse(responseCode = "500", description = "Server error", content = @Content(schema = @Schema(name = "Server error", implementation = ApiError.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
})
@RestController
@RequestMapping("/services/v3/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Operation(summary = "Get all profiles")
    @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(name = "Ok", implementation = UserProfileDTO.class), mediaType = MediaType.APPLICATION_JSON_VALUE))
    @GetMapping
    public ResponseEntity<List<UserProfileDTO>> getAllProfiles() {
        List<UserProfileDTO> profileDTOs = userProfileService.findAllProfiles();
        return ResponseEntity.ok(profileDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDTO> getProfileById(@PathVariable String id) {
        UserProfileDTO profile = userProfileService.findProfileById(id);
        if (profile == null) {
            throw new IllegalArgumentException();
        }

        return ResponseEntity.ok(profile);
    }

    @PostMapping
    public ResponseEntity<UserProfileDTO> createProfile(@RequestBody UserProfileDTO userProfileDTO) {
        // UserProfile profile = convertToEntity(userProfileDTO);
        userProfileService.createProfile(userProfileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileDTO);
    }

    @PutMapping
    public ResponseEntity<UserProfileDTO> updateProfile(
            @PathVariable String id,
            @RequestBody UserProfileDTO userProfileDTO) {
        UserProfileDTO updatedProfileDTO = userProfileService.updateProfile(userProfileDTO);
        return ResponseEntity.ok(updatedProfileDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfileById(@PathVariable String id) {
        userProfileService.deleteProfileById(id);
        return ResponseEntity.noContent().build();
    }
}
