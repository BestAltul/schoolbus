package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.entities.Radio;
import com.wny.schoolbus.entities.SimCard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="radio")
public class RadioImpl implements Radio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="sim_card_id")
    private final SimCardImpl simCard;

    private final String IMEI;
}
