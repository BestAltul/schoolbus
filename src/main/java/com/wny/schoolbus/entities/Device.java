package com.wny.schoolbus.entities;

import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.entities.impl.SimCardImpl;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
public abstract class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sim_card_id")
    protected SimCardImpl simCard;

    public abstract SchoolBusImpl getSchoolBus();

    protected String IMEI;
}
