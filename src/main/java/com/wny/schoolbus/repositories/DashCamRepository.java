package com.wny.schoolbus.repositories;

import com.wny.schoolbus.entities.impl.DashCamImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DashCamRepository extends JpaRepository<DashCamImpl,Integer> {
    List<DashCamImpl> findAll();
}
