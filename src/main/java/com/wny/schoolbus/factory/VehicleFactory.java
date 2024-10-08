package com.wny.schoolbus.factory;

import com.wny.schoolbus.entities.Vehicle;
import com.wny.schoolbus.entities.impl.DashCamImpl;
import com.wny.schoolbus.entities.impl.RadioImpl;
import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.enums.BusType;
import com.wny.schoolbus.enums.Terminal;

public interface VehicleFactory {
    SchoolBusImpl createVehicle(String name, BusType busType, Terminal terminal, DashCamImpl dashCam, RadioImpl radio);
}
