package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.entities.Employee;
import com.wny.schoolbus.enums.EmployeeStatus;
import com.wny.schoolbus.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    public EmployeeServiceImplTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addEmployee() {
        Employee employee = Employee.builder()
                .firstName("Paul")
                .lastName("McCartney")
                .phoneNumber("7169999999")
                .employeeStatus(EmployeeStatus.WORKING).build();

        employeeService.addEmployee(employee);

        verify(employeeRepository,times(1)).save(employee);

    }

    @Test
    void getEmployee() {

        Employee employee = Employee.builder()
                .id("1")
                .firstName("John")
                .lastName("McCartney")
                .phoneNumber("7169999999")
                .employeeStatus(EmployeeStatus.WORKING).build();

        when(employeeRepository.findById("1")).thenReturn(Optional.of(employee));

        Optional<Employee> result = employeeService.getEmployee("1");

        assertNotNull(result.isPresent());

        Employee employeeResult = result.get();

        assertEquals("John",employeeResult.getFirstName());
        assertEquals("McCartney",employeeResult.getLastName());
        assertEquals("7169999999",employeeResult.getPhoneNumber());
        assertEquals(EmployeeStatus.WORKING,employeeResult.getEmployeeStatus());

        verify(employeeRepository,times(1)).findById("1");
    }

    @Test
    void getAllEmployees() {
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void deleteEmployee() {
    }
}