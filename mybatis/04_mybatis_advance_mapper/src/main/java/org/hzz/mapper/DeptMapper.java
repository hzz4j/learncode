package org.hzz.mapper;

import org.hzz.pojo.DeptEmpDTO;

import java.util.List;

public interface DeptMapper {

    List<DeptEmpDTO> selectDeptAndEmp();
    List<DeptEmpDTO> nestingQuery();
}
