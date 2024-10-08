package com.wny.schoolbus.factory.impl;

import com.wny.schoolbus.entities.Vehicle;
import com.wny.schoolbus.entities.impl.DashCamImpl;
import com.wny.schoolbus.entities.impl.RadioImpl;
import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.enums.BusType;
import com.wny.schoolbus.enums.Terminal;
import com.wny.schoolbus.factory.VehicleFactory;

public class SchoolBusFactoryImpl implements VehicleFactory {
    @Override
    public SchoolBusImpl createVehicle(String name, BusType busType,Terminal terminal, DashCamImpl dashCam, RadioImpl radio) {
        return new SchoolBusImpl(name,busType,terminal,dashCam,radio);
    }
}
