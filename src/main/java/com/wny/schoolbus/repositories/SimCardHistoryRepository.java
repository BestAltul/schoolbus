package com.wny.schoolbus.repositories;

import com.wny.schoolbus.entities.Device;
import com.wny.schoolbus.entities.SimCardHistory;
import com.wny.schoolbus.entities.impl.DashCamImpl;
import com.wny.schoolbus.entities.impl.SimCardHistoryImpl;
import com.wny.schoolbus.entities.impl.SimCardImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimCardHistoryRepository extends JpaRepository<SimCardHistoryImpl,Integer> {
    SimCardHistoryImpl findByDeviceAndEndDateIsNull(Device device);

    SimCardHistoryImpl findTopBySimCardOrderByStartDateDesc(SimCardImpl simCard);

    List<SimCardHistoryImpl> getSimCardHistoryBySimCardId(Integer simCardId);

    List<SimCardHistoryImpl> getSimCardHistoryByDeviceId(Integer simCardId);


}
