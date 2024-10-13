package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.entities.impl.Radio;
import com.wny.schoolbus.repositories.RadioRepository;
import com.wny.schoolbus.services.RadioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RadioServiceImpl implements RadioService {

    private final RadioRepository radioRepository;

    public List<Radio> getAllRadios() {
        return radioRepository.findAll();
    }

    public void save(Radio radio) {
        radioRepository.save(radio);
    }

}
