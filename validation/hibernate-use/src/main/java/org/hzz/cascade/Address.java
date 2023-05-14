package org.hzz.cascade;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Address {
    @NotBlank(message = "省份不能为空")
    private String province;
    @NotBlank(message = "城市不能为空")
    private String city;
}
