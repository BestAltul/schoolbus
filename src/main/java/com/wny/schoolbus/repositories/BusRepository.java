package com.wny.schoolbus.repositories;

import com.wny.schoolbus.entities.impl.SchoolBusImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository  extends JpaRepository<SchoolBusImpl, String> {
    List<SchoolBusImpl> findAll();
}
