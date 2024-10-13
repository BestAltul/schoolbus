package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.entities.impl.Radio;
import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import com.wny.schoolbus.repositories.BusRepository;
import com.wny.schoolbus.services.BusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import com.wny.schoolbus.entities.impl.DashCam;

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
            DashCam dashCam = entityManager.find(DashCam.class, bus.getDashCam().getId());
            Radio radio = entityManager.find(Radio.class,bus.getRadio().getId());
            bus.setDashCam(dashCam);
            bus.setRadio(radio);
            entityManager.persist(bus);
        } else {
            DashCam dashCam = entityManager.find(DashCam.class, bus.getDashCam().getId());
            Radio radio = entityManager.find(Radio.class,bus.getRadio().getId());
            bus.setRadio(radio);
            bus.setDashCam(dashCam);
            bus = entityManager.merge(bus);
        }
    }

}
