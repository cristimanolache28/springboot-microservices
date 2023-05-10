package com.l2c.employee.service.impl;

import com.l2c.employee.dto.APIResponseDto;
import com.l2c.employee.dto.DepartmentDto;
import com.l2c.employee.dto.EmployeeDto;
import com.l2c.employee.entity.Employee;
import com.l2c.employee.exception.ResourceNotFoundException;
import com.l2c.employee.mapper.AutoEmployeeMapper;
import com.l2c.employee.repository.EmployeeRepository;
import com.l2c.employee.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    private AutoEmployeeMapper employeeMapper;
    private WebClient webClient;

    // use MapStruct for converting DTO to entity and entity to DTO
    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        // convert DTO to entity
        Employee employee = employeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        // convert entity to DTO
        return employeeMapper.mapToEmployeeDto(savedEmployee);
    }

    // use ModelMapper for converting from entity to DTO
    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );

        DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();

        // convert entity to DTO
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setDepartment(departmentDto);
        apiResponseDto.setEmployee(employeeDto);

        return apiResponseDto;
    }
}
