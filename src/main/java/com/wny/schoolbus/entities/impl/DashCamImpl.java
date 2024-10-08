package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.entities.Device;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
public class DashCamImpl extends Device{

    private String DRID;

    @OneToMany(mappedBy = "device")
    private List<SimCardHistoryImpl> simCardHistory;

    public DashCamImpl() {
        super("");
    }

    public DashCamImpl(String name, String drid, String imei, SimCardImpl simCard) {
        super(name);
        this.DRID = drid;
        this.IMEI = imei;
        this.simCard = simCard;
    }

    @OneToOne(mappedBy = "dashCam")
    private SchoolBusImpl schoolBus;

    @Override
    public SchoolBusImpl getSchoolBus(){
        return this.schoolBus;
    }

    @Override
    public String toString(){
        return this.getName()+" "+this.DRID;
    }

    public String getDescription(){
        return this.getName()+" "+DRID;
    }

}
