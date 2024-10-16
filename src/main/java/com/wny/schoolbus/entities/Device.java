package com.wny.schoolbus.entities;

import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NonNull
    protected String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sim_card_id")
    protected SimCard simCard;

    public abstract SchoolBusImpl getSchoolBus();

    public Device(@NonNull String name) {
        this.name = name;
    }

    protected String IMEI;
}
