package com.wny.schoolbus.services;

import com.wny.schoolbus.entities.impl.SchoolBusImpl;

import java.util.List;

public interface BusService {
    List<SchoolBusImpl> getAllBuses();

    void save(SchoolBusImpl bus);
}
