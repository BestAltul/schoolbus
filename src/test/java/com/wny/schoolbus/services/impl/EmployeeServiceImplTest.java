package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.dtos.EmployeeDTO;
import com.wny.schoolbus.entities.Employee;
import com.wny.schoolbus.enums.EmployeeStatus;
import com.wny.schoolbus.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ModelMapper modelMapper;

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
        Employee employee1 = Employee.builder()
                .firstName("Paul")
                .lastName("McCartney")
                .phoneNumber("7169999999")
                .employeeStatus(EmployeeStatus.WORKING).build();
        employeeRepository.save(employee1);
        Employee employee2 = Employee.builder()
                .firstName("John")
                .lastName("Lennon")
                .phoneNumber("7169999999")
                .employeeStatus(EmployeeStatus.WORKING).build();
        employeeRepository.save(employee2);

        Employee employee3 = Employee.builder()
                .firstName("Ringo")
                .lastName("Starr")
                .phoneNumber("7169999999")
                .employeeStatus(EmployeeStatus.WORKING).build();
        employeeRepository.save(employee3);

        List<Employee> mockEmployeesList = List.of(employee1,employee2,employee3);

        when(employeeRepository.findAll()).thenReturn(mockEmployeesList);

        List<Employee> employees = employeeService.getAllEmployees();

        assertEquals(3,employees.size(),"Number of employees should match the number of saved employees");

        verify(employeeRepository,times(1)).findAll();
    }

    @Test
    void updateEmployee() {

        Employee employee = new Employee();

        employee.setFirstName("John");

        EmployeeDTO employeeDTO = new EmployeeDTO("John","NotLehnon","9999999999",EmployeeStatus.WORKING);

        doNothing().when(modelMapper).map(employeeDTO,employee);

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee updatedEmployee = employeeService.updateEmployee(employee,employeeDTO);

        verify(modelMapper,times(1)).map(employeeDTO,employee);
        verify(employeeRepository,times(1)).save(employee);

        assertEquals(employee,updatedEmployee);

    }

    @Test
    void deleteEmployee() {

        String employeeId = "1";

        Employee employee = new Employee();
        employee.setId(employeeId);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));

        ResponseEntity<Void> response = employeeService.deleteEmployee(employeeId);

        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
        verify(employeeRepository,times(1)).deleteById(employeeId);

    }
}