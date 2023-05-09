package com.l2c.employee.mapper;

import com.l2c.employee.dto.EmployeeDto;
import com.l2c.employee.entity.Employee;

public class EmployeeMapper implements AutoEmployeeMapper{

    @Override
    public EmployeeDto mapToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail()
        );
    }

    @Override
    public Employee mapToEmployee(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        );
    }
}
