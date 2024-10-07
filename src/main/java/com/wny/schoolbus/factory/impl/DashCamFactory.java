package com.wny.schoolbus.factory.impl;

import com.wny.schoolbus.entities.Device;
import com.wny.schoolbus.entities.impl.DashCamImpl;
import com.wny.schoolbus.entities.impl.SimCardImpl;
import com.wny.schoolbus.factory.DeviceFactory;

public class DashCamFactory implements DeviceFactory {
    @Override
    public DashCamImpl createDevice(String name, String DRID, String IMEI, SimCardImpl simCard) {
        return new DashCamImpl(name,DRID,IMEI,simCard);
    }
}
