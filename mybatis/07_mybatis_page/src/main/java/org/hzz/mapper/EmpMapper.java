package org.hzz.mapper;


import org.hzz.pojo.Emp;
import org.hzz.pojo.EmpDeptDTO;

import java.util.List;

public interface EmpMapper {
    List<Emp> selectEmp();
}
