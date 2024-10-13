package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.entities.Device;
import com.wny.schoolbus.entities.SimCard;
import com.wny.schoolbus.entities.SimCardHistory;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
public class DashCam extends Device{

    private String DRID;

    @OneToMany(mappedBy = "device")
    private List<SimCardHistory> simCardHistory;

    public DashCam() {
        super();
    }

    public DashCam(String name, String drid, String imei, SimCard simCard) {
        super(name);
        this.DRID = drid;
        this.IMEI = imei;
        this.simCard = simCard;
    }

    @OneToOne(mappedBy = "dashCam")
    protected SchoolBusImpl schoolBus;

    @Override
    public SchoolBusImpl getSchoolBus(){
        return this.schoolBus;
    }

    @Override
    public String toString(){
        return this.name+" "+this.DRID;
    }

    public String getDescription(){
        return this.name+" "+DRID;
    }

}
