package com.wny.schoolbus.factory;

import com.wny.schoolbus.entities.Device;
import com.wny.schoolbus.entities.SimCard;

public interface DeviceFactory {
    Device createDevice(String name, String DRID, String IMEI, SimCard simCard);
}
