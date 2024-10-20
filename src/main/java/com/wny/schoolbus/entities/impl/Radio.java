package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.entities.Device;
import com.wny.schoolbus.entities.SimCard;
import com.wny.schoolbus.entities.SimCardHistory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
public class Radio extends Device {

    @OneToMany(mappedBy = "device")
    private List<SimCardHistory> simCardHistory;


    public Radio(String name, String imei, SimCard simCard) {
        super(name);
        this.IMEI = imei;
        this.simCard = simCard;
    }
    public Radio() {
        super();
    }

    @OneToOne(mappedBy = "radio")
    private SchoolBusImpl schoolBus;

    @Override
    public SchoolBusImpl getSchoolBus(){
        return this.schoolBus;
    }

    @Override
    public String toString(){
        return getName()+" "+this.getDescription();
    }

    public String getDescription(){
        return getName()+" "+this.IMEI;
    }

}
