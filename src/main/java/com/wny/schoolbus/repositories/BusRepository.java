package com.wny.schoolbus.repositories;

import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepository  extends JpaRepository<SchoolBusImpl, Integer> {
    List<SchoolBusImpl> findAll();
}
