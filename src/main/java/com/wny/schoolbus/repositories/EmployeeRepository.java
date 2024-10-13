package com.wny.schoolbus.repositories;

import com.wny.schoolbus.entities.Employee;
import com.wny.schoolbus.entities.impl.EmployeeImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeImpl,String> {

    Optional<EmployeeImpl> findById(String id);

    List<EmployeeImpl> getAllEmployees();
}
