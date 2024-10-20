package com.wny.schoolbus.controllers;

import com.wny.schoolbus.dtos.EmployeeDTO;
import com.wny.schoolbus.entities.Employee;
import com.wny.schoolbus.services.impl.EmployeeServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v3/employee-management")
public class EmployeeController {

    private EmployeeServiceImpl employeeService;
    private ModelMapper modelMapper;


    @PostMapping()
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO){

        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        Employee savedEmployee = employeeService.addEmployee(employee);

        EmployeeDTO savedEmployeeDTO = modelMapper.map(savedEmployee, EmployeeDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployeeDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity getEmployeeById(@PathVariable String id){

        Optional<Employee> employeeOptional = employeeService.getEmployee(id);

        if(employeeOptional.isPresent()){
            EmployeeDTO employeeDTO = modelMapper.map(employeeOptional.get(), EmployeeDTO.class);
            return ResponseEntity.ok(employeeDTO);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){

        List<Employee> employeeList = employeeService.getAllEmployees();
        List<EmployeeDTO> employeeDTOList = employeeList.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDTOList);

    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable String id, @RequestBody EmployeeDTO employeeDTO){

        Optional<Employee> employeeOptional = employeeService.getEmployee(id);

        if(employeeOptional.isPresent()){

            Employee updatedEmployee = employeeService.updateEmployee(employeeOptional.get(),employeeDTO);

            EmployeeDTO updatedEmployeeDTO = modelMapper.map(updatedEmployee, EmployeeDTO.class);

            return ResponseEntity.ok(updatedEmployeeDTO);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id){

        return employeeService.deleteEmployee(id);

    }

}
