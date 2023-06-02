package org.hzz.spring.mapper;

import org.hzz.spring.dto.DivisionDTO;
import org.hzz.spring.dto.EmployeeDTO;
import org.hzz.spring.entity.Division;
import org.hzz.spring.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class EmployeeAndDTOMapper {
    @Mapping(target = "id", source = "dto.employeeId")
    @Mapping(target = "name", source = "dto.employeeName")
    @Mapping(target = "startDt", source = "dto.employeeStartDt", dateFormat = "dd-MM-yyyy HH:mm:ss")
    public abstract Employee employeeDTOToEmployee(EmployeeDTO dto);

    public abstract Division divisionDTOtoDivision(DivisionDTO dto);

}
