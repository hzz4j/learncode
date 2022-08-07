package org.hzz.mapper;

import org.hzz.pojo.Dept;
import org.hzz.pojo.DeptEmpDTO;

import java.util.List;

public interface DeptMapper {
    List<Dept> selectDept(Dept dept);
}
