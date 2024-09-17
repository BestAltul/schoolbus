package com.wny.schoolbus.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wny.schoolbus.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "user_profiles", uniqueConstraints =
@UniqueConstraint(columnNames = {"email"}))
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserCredentials userCredentials;


}