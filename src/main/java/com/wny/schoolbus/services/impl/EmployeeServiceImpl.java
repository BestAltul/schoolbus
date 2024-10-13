package com.wny.schoolbus.services.impl;

import com.wny.schoolbus.dtos.EmployeeDTO;
import com.wny.schoolbus.entities.impl.EmployeeImpl;
import com.wny.schoolbus.repositories.EmployeeRepository;
import com.wny.schoolbus.services.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;

    public EmployeeImpl addEmployee(EmployeeImpl employee){
        return employeeRepository.save(employee);
    }

    public Optional<EmployeeImpl> getEmployee(String id){
        return employeeRepository.findById(id);
    }

    public List<EmployeeImpl> getAllEmployees(){
        return employeeRepository.getAllEmployees();
    }

    public EmployeeImpl updateEmployee(EmployeeImpl employee, EmployeeDTO employeeDTO) {

        modelMapper.map(employeeDTO, employee);

        EmployeeImpl updatedEmployee = employeeRepository.save(employee);

        return updatedEmployee;

    }

    public ResponseEntity<Void> deleteEmployee(String id){

        Optional<EmployeeImpl> employeeOpt = employeeRepository.findById(id);

        if (employeeOpt.isPresent()) {
            employeeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
