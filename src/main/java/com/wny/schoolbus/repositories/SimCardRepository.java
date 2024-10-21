package com.wny.schoolbus.repositories;

import com.wny.schoolbus.entities.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimCardRepository extends JpaRepository<SimCard, String> {

}
