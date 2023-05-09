package com.l2c.employee.service;

import com.l2c.employee.dto.EmployeeDto;
import com.l2c.employee.entity.Employee;

public interface EmployeeService {

    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(Long employeeId);
}
