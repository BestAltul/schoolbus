package com.wny.schoolbus.entities.impl;

import com.wny.schoolbus.entities.Employee;
import com.wny.schoolbus.enums.EmployeeStatus;
import jakarta.persistence.*;

@Entity
@Table(name="employee")
public class EmployeeImpl implements Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    @Enumerated
    private EmployeeStatus employeeStatus;




}
