package org.hzz.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class JobDetail {
//    @JSONField(serialize = false)
    private long id;
    private String area;
    private String exp;
    private String edu;
    private String salary;
    private String job_type;
    private String cmp;
    private String pv;
    private String title;
    private String jd;
    private int age;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getCmp() {
        return cmp;
    }

    public void setCmp(String cmp) {
        this.cmp = cmp;
    }

    public String getPv() {
        return pv;
    }

    public void setPv(String pv) {
        this.pv = pv;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "JobDetail{" +
                "id=" + id +
                ", area='" + area + '\'' +
                ", exp='" + exp + '\'' +
                ", edu='" + edu + '\'' +
                ", salary='" + salary + '\'' +
                ", job_type='" + job_type + '\'' +
                ", cmp='" + cmp + '\'' +
                ", pv='" + pv + '\'' +
                ", title='" + title + '\'' +
                ", jd='" + jd + '\'' +
                ", age=" + age +
                '}';
    }
}
