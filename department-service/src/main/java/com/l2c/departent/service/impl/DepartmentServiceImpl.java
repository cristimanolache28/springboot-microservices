package com.l2c.departent.service.impl;

import com.l2c.departent.dto.DepartmentDto;
import com.l2c.departent.entity.Department;
import com.l2c.departent.exception.ResourceNotFoundException;
import com.l2c.departent.mapper.AutoDepartmentMapper;
import com.l2c.departent.repository.DepartmentRepository;
import com.l2c.departent.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    private AutoDepartmentMapper autoDepartmentMapper;

    private ModelMapper modelMapper;

    // Converting using MapStrut
    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        // convert department dto to department entity
        Department department = autoDepartmentMapper.mapToDepartment(departmentDto);
        Department departmentSaved = departmentRepository.save(department);
        // convert department entity to department DTO
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
        return AutoDepartmentMapper.MAPPER.mapToDepartmentDto(department);
    }

}
