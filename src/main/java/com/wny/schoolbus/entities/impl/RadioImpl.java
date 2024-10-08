package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.entities.Device;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
public class RadioImpl extends Device {

    @OneToMany(mappedBy = "device")
    private List<SimCardHistoryImpl> simCardHistory;


    public RadioImpl(String name, String imei, SimCardImpl simCard) {
        super(name);
        this.IMEI = imei;
        this.simCard = simCard;
    }

    @OneToOne(mappedBy = "radio")
    private SchoolBusImpl schoolBus;

    @Override
    public SchoolBusImpl getSchoolBus(){
        return this.schoolBus;
    }

    @Override
    public String toString(){
        return this.getName()+" "+this.getDescription();
    }

    public String getDescription(){
        return this.getName()+" "+this.IMEI;
    }

}
