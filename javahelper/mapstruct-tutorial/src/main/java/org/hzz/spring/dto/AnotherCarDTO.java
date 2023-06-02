package org.hzz.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnotherCarDTO {
    private int id;
    private String name;
    private FuelType fuelType;
}
