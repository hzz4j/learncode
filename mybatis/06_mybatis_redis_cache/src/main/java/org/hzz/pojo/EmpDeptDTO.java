package org.hzz.pojo;

public class EmpDeptDTO extends Emp{
    private Dept dept;

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "EmpDeptDTO{" +
                "dept=" + dept +
                "} " + super.toString();
    }
}
