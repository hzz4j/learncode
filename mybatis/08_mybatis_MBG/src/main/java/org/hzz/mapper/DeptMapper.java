package org.hzz.mapper;

import java.util.List;
import org.hzz.pojo.Dept;

public interface DeptMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(Dept row);

    Dept selectByPrimaryKey(Integer deptId);

    List<Dept> selectAll();

    int updateByPrimaryKey(Dept row);
}