package com.wny.schoolbus.factory.impl;

import com.wny.schoolbus.entities.impl.DashCam;
import com.wny.schoolbus.entities.SimCard;
import com.wny.schoolbus.factory.DeviceFactory;

public class DashCamFactoryImpl implements DeviceFactory {
    @Override
    public DashCam createDevice(String name, String DRID, String IMEI, SimCard simCard) {
        return new DashCam(name,DRID,IMEI,simCard);
    }
}
