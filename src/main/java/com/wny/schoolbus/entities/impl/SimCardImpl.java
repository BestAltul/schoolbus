package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.entities.SimCard;
import com.wny.schoolbus.enums.SimCardCarrier;
import com.wny.schoolbus.enums.SimCardType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="sim_card")
public class SimCardImpl implements SimCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Enumerated(EnumType.STRING)
    private SimCardType simCardType;

    @NonNull
    @Enumerated(EnumType.STRING)
    private SimCardCarrier simCardCarrier;

    @NonNull
    private String simCardNumber;

    @OneToMany(mappedBy = "simCard")
    private List<RadioImpl> radios;

    @OneToMany(mappedBy = "simCard")
    private List<DashCamImpl> dashCams;

}
