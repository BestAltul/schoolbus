package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.repositories.BusRepository;
import com.wny.schoolbus.services.BusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    @Override
    public List<SchoolBusImpl> getAllBuses() {

        return  busRepository.findAll();
    }

    @Override
    @Transactional
    public void save(SchoolBusImpl bus) {
        busRepository.save(bus);
    }

}
