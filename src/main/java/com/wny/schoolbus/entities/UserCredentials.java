package com.wny.schoolbus.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user_credentials")
public class UserCredentials {
    @Id
    private String userId;

    @NotBlank(message = "User login cannot be blank")
    @Pattern(regexp = "^[a-aZ-Z0-9_]+$",message = "User login must be alphanumeric")
    private String userLogin;
    private String hashedPassword;
    private String salt;

    @MapsId
    @JoinColumn(name = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private UserProfile userProfile;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

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

    @PrePersist
    public void onSave() {
        createdDate = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        updatedDate = new Date();
    }
}