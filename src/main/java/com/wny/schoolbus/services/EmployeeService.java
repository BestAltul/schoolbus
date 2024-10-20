package com.wny.schoolbus.services;

import com.wny.schoolbus.entities.Employee;

import java.util.Optional;

public interface EmployeeService {

    Optional<Employee> getEmployee(String id);
}
