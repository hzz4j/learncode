package org.hzz.mapper;

import lombok.extern.slf4j.Slf4j;
import org.hzz.spring.dto.DivisionDTO;
import org.hzz.spring.dto.EmployeeDTO;
import org.hzz.spring.entity.Employee;
import org.hzz.spring.mapper.EmployeeAndDTOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EmployAndDTOTest {
    @Autowired
    private EmployeeAndDTOMapper employeeAndDTOMapper;

    @Test
    public void testChildren(){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(1);
        employeeDTO.setEmployeeName("Q10Viking");
        employeeDTO.setEmployeeStartDt(new Date());
        employeeDTO.setDivision(new DivisionDTO(1,"IT"));

        Employee employee = employeeAndDTOMapper.employeeDTOToEmployee(employeeDTO);
        log.info(employee.toString());
        // org.hzz.mapper.EmployAndDTOTest          : Employee(id=1, name=Q10Viking, division=Division(id=1, name=IT), startDt=02-06-2023 22:02:46)
    }

}
