package org.hzz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private long id;
    private String name;
    private String contactNumber;
    private String workingArea;

}
