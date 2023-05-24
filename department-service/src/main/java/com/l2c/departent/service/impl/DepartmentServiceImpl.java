package com.l2c.departent.service.impl;

import com.l2c.departent.dto.DepartmentDto;
import com.l2c.departent.entity.Department;
import com.l2c.departent.exception.ResourceNotFoundException;
import com.l2c.departent.mapper.AutoDepartmentMapper;
import com.l2c.departent.repository.DepartmentRepository;
import com.l2c.departent.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentRepository departmentRepository;
    private AutoDepartmentMapper autoDepartmentMapper;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        Department department = autoDepartmentMapper.mapToDepartment(departmentDto);
        Department departmentSaved = departmentRepository.save(department);
        return autoDepartmentMapper.mapToDepartmentDto(departmentSaved);
    }

    // Converting using ModelMapper
    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {
        Department department = departmentRepository.findByDepartmentCode(departmentCode);
        if (department == null) {
            throw new ResourceNotFoundException("Department", "code", departmentCode);
        }
        // Convert to DTO from entity
        return autoDepartmentMapper.mapToDepartmentDto(department);
    }

}
