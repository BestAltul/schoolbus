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
        drid = drid;
        imei = imei;
        simCard = simCard;

    }

    @Override
    public String toString(){
        return this.getName()+" "+this.DRID;
    }

    public String getDescription(){
        return this.getName()+" "+DRID;
    }

}
