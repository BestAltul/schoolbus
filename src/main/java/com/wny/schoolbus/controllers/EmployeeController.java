package com.wny.schoolbus.controllers;

import com.wny.schoolbus.dtos.EmployeeDTO;
import com.wny.schoolbus.entities.impl.EmployeeImpl;
import com.wny.schoolbus.services.EmployeeService;
import com.wny.schoolbus.services.impl.EmployeeServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeServiceImpl employeeService;
    private ModelMapper modelMapper;


    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> addEmployee(@RequestBody EmployeeDTO employeeDTO){

        EmployeeImpl employee = modelMapper.map(employeeDTO,EmployeeImpl.class);

        EmployeeImpl savedEmployee = employeeService.addEmployee(employee);

        EmployeeDTO savedEmployeeDTO = modelMapper.map(savedEmployee, EmployeeDTO.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployeeDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity getEmployeeById(@PathVariable String id){

        Optional<EmployeeImpl> employeeOptional = employeeService.getEmployee(id);

        if(employeeOptional.isPresent()){
            EmployeeDTO employeeDTO = modelMapper.map(employeeOptional.get(), EmployeeDTO.class);
            return ResponseEntity.ok(employeeDTO);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(){

        List<EmployeeImpl> employeeList = employeeService.getAllEmployees();
        List<EmployeeDTO> employeeDTOList = employeeList.stream().map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(employeeDTOList);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable String id, @RequestBody EmployeeDTO employeeDTO){

        Optional<EmployeeImpl> employeeOptional = employeeService.getEmployee(id);

        if(employeeOptional.isPresent()){

            EmployeeImpl updatedEmployee = employeeService.updateEmployee(employeeOptional.get(),employeeDTO);

            EmployeeDTO updatedEmployeeDTO = modelMapper.map(updatedEmployee, EmployeeDTO.class);

            return ResponseEntity.ok(updatedEmployeeDTO);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id){

        return employeeService.deleteEmployee(id);

    }

}
