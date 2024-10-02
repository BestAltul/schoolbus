package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.entities.SimCardHistory;
import com.wny.schoolbus.entities.impl.DashCamImpl;
import com.wny.schoolbus.entities.impl.SimCardHistoryImpl;
import com.wny.schoolbus.entities.impl.SimCardImpl;
import com.wny.schoolbus.repositories.SimCardHistoryRepository;
import com.wny.schoolbus.repositories.SimCardRepository;
import com.wny.schoolbus.services.SimCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimCardServiceImpl implements SimCardService {
    private final SimCardRepository simCardRepository;
    private final SimCardHistoryRepository simCardHistoryRepository;

    @Override
    public List<SimCardImpl> getAllSimCards() {
        return simCardRepository.findAll();
    }

    public void save(SimCardImpl simCard){
        simCardRepository.save(simCard);
    }

    public void closePreviousSimCardHistory(DashCamImpl dashCam) {
        SimCardHistoryImpl currentHistory = simCardHistoryRepository.findCurrentByDashCam(dashCam);
        if (currentHistory != null) {
            currentHistory.setEndDate(LocalDate.now());
            simCardHistoryRepository.save(currentHistory);
        }
    }

    public void saveSimCardHistory(SimCardHistoryImpl simCardHistory) {
        simCardHistoryRepository.save(simCardHistory);
    }

    public SimCardHistoryImpl getLastSimCardHistory(SimCardImpl simCard) {
        return simCardHistoryRepository.findTopBySimCardOrderByStartDateDesc(simCard);
    }

    public List<SimCardHistoryImpl> getSimCardHistoryBySimCardId(Integer simCardId){
        return simCardHistoryRepository.getSimCardHistoryBySimCardId(simCardId);
    }
}
