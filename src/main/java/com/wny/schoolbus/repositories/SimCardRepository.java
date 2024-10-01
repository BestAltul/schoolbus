package com.wny.schoolbus.repositories;

import com.wny.schoolbus.entities.impl.SimCardImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SimCardRepository extends JpaRepository<SimCardImpl, Integer> {
    List<SimCardImpl> findAll();
}
