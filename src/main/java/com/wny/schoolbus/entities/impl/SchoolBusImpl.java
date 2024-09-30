package com.wny.schoolbus.entities.impl;

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

    @Enumerated(EnumType.STRING)
    @NonNull
    private BusType busType;

    @NonNull
    private String name;

    @Enumerated(EnumType.STRING)
    @NonNull
    private Terminal terminal;
}
