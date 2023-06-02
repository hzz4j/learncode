package org.hzz.spring.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Employee {
    private int id;
    private String name;
    private Division division;
    private String startDt;

}
