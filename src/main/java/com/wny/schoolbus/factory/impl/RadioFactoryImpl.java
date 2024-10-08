package com.wny.schoolbus.factory.impl;

import com.wny.schoolbus.entities.impl.RadioImpl;
import com.wny.schoolbus.entities.impl.SimCardImpl;
import com.wny.schoolbus.factory.DeviceFactory;

public class RadioFactoryImpl implements DeviceFactory {
    @Override
    public RadioImpl createDevice(String name, String DRID, String IMEI, SimCardImpl simCard) {
        return new RadioImpl(name,IMEI,simCard);
    }
}
