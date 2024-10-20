package com.wny.schoolbus.entities;

import com.wny.schoolbus.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus employeeStatus;

    @ManyToMany(mappedBy = "drivers", fetch = FetchType.LAZY)
    private List<Route> routesAsDriverList;

    @ManyToMany(mappedBy = "monitors", fetch = FetchType.LAZY)
    private List<Route> routesAsMonitorLists;
}
