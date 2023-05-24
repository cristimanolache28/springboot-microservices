package com.l2c.employee.service.impl;

import com.l2c.employee.dto.APIResponseDto;
import com.l2c.employee.dto.DepartmentDto;
import com.l2c.employee.dto.EmployeeDto;
import com.l2c.employee.entity.Employee;
import com.l2c.employee.exception.ResourceNotFoundException;
import com.l2c.employee.mapper.AutoEmployeeMapper;
import com.l2c.employee.repository.EmployeeRepository;
import com.l2c.employee.service.APIClient;
import com.l2c.employee.service.EmployeeService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    private AutoEmployeeMapper employeeMapper;

    private APIClient apiClient;
    private WebClient webClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.mapToEmployee(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto savedEmployeeDto = employeeMapper.mapToEmployeeDto(savedEmployee);
        return savedEmployeeDto;
    }

    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        LOGGER.info("inside getEmployeeById() method");
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );

        DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();

        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setDepartment(departmentDto);
        apiResponseDto.setEmployee(employeeDto);

        return apiResponseDto;
    }

    public APIResponseDto getDefaultDepartment(Long employeeId, Exception exception) {
        LOGGER.info("inside getDefaultDepartment() method");
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Employee", "id", employeeId)
        );

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("R&C Department");
        departmentDto.setDepartmentCode("RD001");
        departmentDto.setDepartmentDescription("Research for Development Department");

        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setDepartment(departmentDto);
        apiResponseDto.setEmployee(employeeDto);

        return apiResponseDto;
    }
}
