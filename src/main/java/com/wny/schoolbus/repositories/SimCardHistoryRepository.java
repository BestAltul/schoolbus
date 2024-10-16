package com.wny.schoolbus.repositories;

import com.wny.schoolbus.entities.Device;
import com.wny.schoolbus.entities.SimCardHistory;
import com.wny.schoolbus.entities.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimCardHistoryRepository extends JpaRepository<SimCardHistory,String> {
    SimCardHistory findByDeviceAndEndDateIsNull(Device device);

    SimCardHistory findTopBySimCardOrderByStartDateDesc(SimCard simCard);

    List<SimCardHistory> getSimCardHistoryBySimCardId(String simCardId);

    List<SimCardHistory> getSimCardHistoryByDeviceId(String simCardId);


}
