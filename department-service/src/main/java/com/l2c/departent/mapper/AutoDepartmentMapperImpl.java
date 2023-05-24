package com.l2c.departent.mapper;

import com.l2c.departent.dto.DepartmentDto;
import com.l2c.departent.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class AutoDepartmentMapperImpl implements AutoDepartmentMapper {

    @Override
    public DepartmentDto mapToDepartmentDto(Department department) {
        DepartmentDto departmentDto = new DepartmentDto(
                department.getId(),
                department.getDepartmentName(),
                department.getDepartmentDescription(),
                department.getDepartmentCode()
        );
        return departmentDto;
    }

    @Override
    public Department mapToDepartment(DepartmentDto departmentDto) {
        Department department = new Department(
                departmentDto.getId(),
                departmentDto.getDepartmentName(),
                departmentDto.getDepartmentDescription(),
                departmentDto.getDepartmentCode()
        );
        return department;
    }

}
