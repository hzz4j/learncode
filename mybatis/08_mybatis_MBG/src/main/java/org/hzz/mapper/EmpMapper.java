package org.hzz.mapper;

import java.util.List;
import org.hzz.pojo.Emp;

public interface EmpMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Emp row);

    Emp selectByPrimaryKey(Integer id);

    List<Emp> selectAll();

    int updateByPrimaryKey(Emp row);
}