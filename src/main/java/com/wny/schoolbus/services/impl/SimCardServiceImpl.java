package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.entities.impl.SimCardImpl;
import com.wny.schoolbus.repositories.SimCardRepository;
import com.wny.schoolbus.services.SimCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimCardServiceImpl implements SimCardService {
    private final SimCardRepository simCardRepository;

    @Override
    public List<SimCardImpl> getAllSimCards() {
        return simCardRepository.findAll();
    }

    public void save(SimCardImpl simCard){
        simCardRepository.save(simCard);
    }
}
