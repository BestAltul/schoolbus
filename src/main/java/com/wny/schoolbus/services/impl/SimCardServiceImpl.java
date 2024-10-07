package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.entities.Device;
import com.wny.schoolbus.entities.SimCardHistory;
import com.wny.schoolbus.entities.impl.DashCamImpl;
import com.wny.schoolbus.entities.impl.SimCardHistoryImpl;
import com.wny.schoolbus.entities.impl.SimCardImpl;
import com.wny.schoolbus.repositories.SimCardHistoryRepository;
import com.wny.schoolbus.repositories.SimCardRepository;
import com.wny.schoolbus.services.SimCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

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

    public void closePreviousSimCardHistory(Device device) {
        SimCardHistoryImpl currentHistory = simCardHistoryRepository.findByDeviceAndEndDateIsNull(device);
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
//    public List<SimCardHistoryImpl> getSimCardHistoryByDashCamId(Integer dashCamId){
//        return simCardHistoryRepository.getSimCardHistoryByDashCamId(dashCamId);
//    }

    public List<SimCardHistoryImpl> getSimCardHistoryByDashCamId(Integer deviceId){
        return simCardHistoryRepository.getSimCardHistoryByDeviceId(deviceId);
    }

    public List<SimCardImpl> getSimCardsByFilter(String filter) {
        // Если фильтр пустой, возвращаем все SIM-карты
        if (filter == null || filter.isEmpty()) {
            return getAllSimCards(); // Предполагается, что у вас есть этот метод
        }

        // Получаем все SIM-карты и фильтруем по номеру
        List<SimCardImpl> allSimCards = getAllSimCards();
        return allSimCards.stream()
                .filter(simCard -> simCard.getSimCardNumber().toLowerCase().contains(filter.toLowerCase()))
                .collect(Collectors.toList());
    }

}
