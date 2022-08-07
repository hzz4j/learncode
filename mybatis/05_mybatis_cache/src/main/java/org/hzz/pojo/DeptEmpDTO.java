package org.hzz.pojo;

import java.util.List;

public class DeptEmpDTO extends Dept{
    private List<Emp> empList;

    public List<Emp> getEmpList() {
        return empList;
    }

    public void setEmpList(List<Emp> empList) {
        this.empList = empList;
    }

    @Override
    public String toString() {
        return "DeptEmpDTO{" +
                "empList=" + empList +
                "} " + super.toString();
    }
}
