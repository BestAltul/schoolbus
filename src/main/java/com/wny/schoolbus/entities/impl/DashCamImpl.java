package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.entities.SimCard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@Table(name="dash_cam")
public class DashCamImpl implements com.wny.schoolbus.entities.DashCam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String DRID;
    private String IMEI;

    @ManyToOne
    @JoinColumn(name = "sim_card_id")
    private SimCardImpl simCard;

    @OneToOne(mappedBy = "dashCam")
    private SchoolBusImpl schoolBus;

    public String getDescription(){
        return this.name+" "+DRID;
    }
}
