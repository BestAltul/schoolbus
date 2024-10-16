package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.dtos.EmployeeDTO;
import com.wny.schoolbus.entities.Employee;
import com.wny.schoolbus.repositories.EmployeeRepository;
import com.wny.schoolbus.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    private ModelMapper modelMapper;

    private RestTemplate restTemplate;

    public Employee addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployee(String id){
        return employeeRepository.findById(id);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee updateEmployee(Employee employee, EmployeeDTO employeeDTO) {

        modelMapper.map(employeeDTO, employee);

        Employee updatedEmployee = employeeRepository.save(employee);

        return updatedEmployee;

    }

    public ResponseEntity<Void> deleteEmployee(String id){

        Optional<Employee> employeeOpt = employeeRepository.findById(id);

        if (employeeOpt.isPresent()) {
            employeeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public void save(Employee employee){
        employeeRepository.save(employee);
    }


}
