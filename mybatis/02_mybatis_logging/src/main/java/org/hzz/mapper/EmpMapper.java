package org.hzz.mapper;

import org.hzz.pojo.Emp;

public interface EmpMapper {
    Emp selectEmp(Integer id);

    Integer insertEmp(Emp emp);

    Integer updateEmp(Emp emp);

    Integer deleteEmp(Integer id);
}
