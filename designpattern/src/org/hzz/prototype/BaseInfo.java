package org.hzz.prototype;

import java.io.Serializable;

public class BaseInfo implements Cloneable, Serializable {
    private String companyName;

    public BaseInfo(String companyName){
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    protected BaseInfo clone() throws CloneNotSupportedException {
        return (BaseInfo)super.clone();
    }

    @Override
    public String toString() {
        return "BaseInfo{" +
                "companyName='" + companyName + '\'' +
                '}';
    }
}
