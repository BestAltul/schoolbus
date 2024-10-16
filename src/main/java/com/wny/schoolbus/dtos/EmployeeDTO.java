package com.wny.schoolbus.dtos;

import com.wny.schoolbus.enums.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private EmployeeStatus employeeStatus;
}
