package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.entities.impl.DashCamImpl;
import com.wny.schoolbus.entities.impl.RadioImpl;
import com.wny.schoolbus.repositories.DashCamRepository;
import com.wny.schoolbus.repositories.RadioRepository;
import com.wny.schoolbus.services.RadioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RadioServiceImpl implements RadioService {

    private final RadioRepository radioRepository;

    public List<RadioImpl> getAllRadios() {
        return radioRepository.findAll();
    }

    public void save(RadioImpl radio) {
        radioRepository.save(radio);
    }

}
