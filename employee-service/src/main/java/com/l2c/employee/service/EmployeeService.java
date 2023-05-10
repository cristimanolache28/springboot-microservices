package com.l2c.employee.service;

import com.l2c.employee.dto.APIResponseDto;
import com.l2c.employee.dto.EmployeeDto;

public interface EmployeeService {

    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    APIResponseDto getEmployeeById(Long employeeId);
}
