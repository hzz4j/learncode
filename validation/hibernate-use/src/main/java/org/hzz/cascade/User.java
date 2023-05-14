package org.hzz.cascade;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class User {
    @NotNull(message = "用户ID不能为空")
    private String userId;
   // -------------级联验证---------------------------
//    @Valid
//    private Address address;

//    @Valid
//    private List<Address> addressList;
    private List< @Valid Address> addressList;
}
