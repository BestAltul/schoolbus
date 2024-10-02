package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.entities.SimCard;
import com.wny.schoolbus.entities.SimCardHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "dashCam")
    private List<SimCardHistoryImpl> simCardHistory;

    @Override
    public String toString(){
        return this.name+" "+this.DRID;
    }

    public String getDescription(){
        return this.name+" "+DRID;
    }
}
