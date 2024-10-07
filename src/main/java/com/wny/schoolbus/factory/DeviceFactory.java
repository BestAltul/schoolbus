package com.wny.schoolbus.factory;

import com.wny.schoolbus.entities.Device;
import com.wny.schoolbus.entities.impl.SimCardImpl;

public interface DeviceFactory {
    Device createDevice(String name, String DRID, String IMEI, SimCardImpl simCard);
}
