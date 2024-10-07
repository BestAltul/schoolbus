package com.wny.schoolbus.repositories;

import com.wny.schoolbus.entities.impl.RadioImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RadioRepository extends JpaRepository<RadioImpl,Integer> {
    List<RadioImpl> findAll();
}
