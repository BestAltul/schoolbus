package com.wny.schoolbus.entities;

import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.entities.impl.SimCardImpl;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sim_card_id")
    private SimCardImpl simCard;

    @OneToOne(mappedBy = "radio")
    private SchoolBusImpl schoolBus;

    private String IMEI;
}
