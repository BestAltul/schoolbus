package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.entities.SimCard;
import com.wny.schoolbus.enums.SimCardCarrier;
import com.wny.schoolbus.enums.SimCardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@Entity
@Table(name="sim_card")
public class SimCardImpl implements SimCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private SimCardType simCardType;
    @Enumerated(EnumType.STRING)
    private SimCardCarrier simCardCarrier;
    private String simCardNumber;

    @OneToMany(mappedBy = "simCard")
    private List<RadioImpl> radios;

    @OneToMany(mappedBy = "simCard")
    private List<DashCamImpl> dashCams;

}
