package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.entities.impl.RadioImpl;
import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.repositories.BusRepository;
import com.wny.schoolbus.services.BusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.wny.schoolbus.entities.impl.DashCamImpl;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SchoolBusImpl> getAllBuses() {

        return  busRepository.findAll();
    }

    @Override
    @Transactional
    public void save(SchoolBusImpl bus) {

        if (bus.getId() == null) {
            DashCamImpl dashCam = entityManager.find(DashCamImpl.class, bus.getDashCam().getId());
            RadioImpl radio = entityManager.find(RadioImpl.class,bus.getRadio().getId());
            bus.setDashCam(dashCam);
            bus.setRadio(radio);
            entityManager.persist(bus);
        } else {
            DashCamImpl dashCam = entityManager.find(DashCamImpl.class, bus.getDashCam().getId());
            RadioImpl radio = entityManager.find(RadioImpl.class,bus.getRadio().getId());
            bus.setRadio(radio);
            bus.setDashCam(dashCam);
            bus = entityManager.merge(bus);
        }
    }

}
