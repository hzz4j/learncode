package org.hzz.feign;

import org.hzz.feign.fallback.AccountServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tcc-account-service",path = "/account",fallback = AccountServiceFallBack.class)
public interface AccountFeignService {

    @RequestMapping("/debit")
    Boolean debit(@RequestParam("userId") String userId,
                  @RequestParam("money") int money);
}
