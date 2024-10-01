package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.annotations.DisplayName;
import com.wny.schoolbus.entities.Vehicle;
import com.wny.schoolbus.enums.BusType;
import com.wny.schoolbus.enums.Terminal;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name="school_bus")
public class SchoolBusImpl implements Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @DisplayName("Name")
    private String name;

    @Enumerated(EnumType.STRING)
    @NonNull
    @DisplayName("Type of bus")
    private BusType busType;


    @Enumerated(EnumType.STRING)
    @NonNull
    @DisplayName("Terminal")
    private Terminal terminal;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="dashcam_id",referencedColumnName = "id")
    @DisplayName("Dash camera")
    private DashCamImpl dashCam;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="radio_id",referencedColumnName = "id")
    @DisplayName("Radio")
    private RadioImpl radio;
}
