package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.entities.Vehicle;
import com.wny.schoolbus.enums.BusType;
import com.wny.schoolbus.enums.FuelType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="school_bus")
@Getter
@Setter
@AllArgsConstructor
public class SchoolBusImpl implements Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private BusType busType;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Getter
    @Setter
    @OneToMany
    private TerminalImpl terminal;

}
