package org.hzz.mapper;

import org.apache.ibatis.annotations.Param;
import org.hzz.pojo.Emp;

import java.util.List;
import java.util.Map;

public interface EmpMapper {
    Emp selectEmp(@Param("id") Integer id, @Param("username") String username);
    Emp selectEmpById(Integer id);
    Emp selectEmpByJavaBean(@Param("id") Integer id, @Param("emp") Emp e);
    Emp selectEmpByMap(Map<String,Object> map);

    Map selectEmpReturnMap(Integer id);
    List<Map> selectEmpReturnListMap();

    Integer insertEmp(Emp emp);

    Integer insertEmpHasSelectKey(Emp emp);

    Integer updateEmp(Emp emp);

    Integer deleteEmp(Integer id);
}
