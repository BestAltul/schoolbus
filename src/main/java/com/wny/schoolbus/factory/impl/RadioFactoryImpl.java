package com.wny.schoolbus.factory.impl;

import com.wny.schoolbus.entities.impl.Radio;
import com.wny.schoolbus.entities.SimCard;
import com.wny.schoolbus.factory.DeviceFactory;

public class RadioFactoryImpl implements DeviceFactory {
    @Override
    public Radio createDevice(String name, String DRID, String IMEI, SimCard simCard) {
        return new Radio(name,IMEI,simCard);
    }
}
