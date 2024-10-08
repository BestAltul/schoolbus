package com.wny.schoolbus.entities;

import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.entities.impl.SimCardImpl;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    protected String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sim_card_id")
    protected SimCardImpl simCard;

    public abstract SchoolBusImpl getSchoolBus();

    public Device(@NonNull String name) {
        this.name = name;
    }

    protected String IMEI;
}
