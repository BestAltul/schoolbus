package com.wny.schoolbus.utils.route;

import com.wny.schoolbus.entities.Employee;
import org.modelmapper.AbstractConverter;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeListToIdListConverter extends AbstractConverter<List<Employee>, List<String>> {

    @Override
    protected List<String> convert(List<Employee> source) {
        return source.stream()
                .map(Employee::getId)
                .collect(Collectors.toList());
    }
}
