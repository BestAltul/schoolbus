package com.wny.schoolbus.repositories;

import com.wny.schoolbus.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School,String> {
}
