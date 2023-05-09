package com.l2c.departent.mapper;

import com.l2c.departent.dto.DepartmentDto;
import com.l2c.departent.entity.Department;
import org.springframework.stereotype.Component;

@Component
public class AutoDepartmentMapperImpl implements AutoDepartmentMapper {

    @Override
    public DepartmentDto mapToDepartmentDto(Department department) {
        if (department == null) {
            return null;
        }

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(department.getId());
        departmentDto.setDepartmentName(department.getDepartmentName());
        departmentDto.setDepartmentDescription(department.getDepartmentDescription());
        departmentDto.setDepartmentCode(department.getDepartmentCode());

        return departmentDto;
    }

    @Override
    public Department mapToDepartment(DepartmentDto departmentDto) {
        if (departmentDto == null) {
            return  null;
        }

        Department department = new Department();
        department.setId(departmentDto.getId());
        department.setDepartmentName(departmentDto.getDepartmentName());
        department.setDepartmentDescription(departmentDto.getDepartmentDescription());
        department.setDepartmentCode(departmentDto.getDepartmentCode());

        return department;
    }
}
