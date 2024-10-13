package com.wny.schoolbus.factory;

import com.wny.schoolbus.entities.impl.DashCam;
import com.wny.schoolbus.entities.impl.Radio;
import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.enums.BusType;
import com.wny.schoolbus.enums.Terminal;

public interface VehicleFactory {
    SchoolBusImpl createVehicle(String name, BusType busType, Terminal terminal, DashCam dashCam, Radio radio);
}
