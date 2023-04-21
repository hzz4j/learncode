package org.hzz.feign.fallback;

import org.hzz.feign.AccountFeignService;
import org.springframework.stereotype.Service;

/**
 * 降级处理
 */
@Service
public class AccountServiceFallBack implements AccountFeignService {
    @Override
    public Boolean debit(String userId, int money) {
        System.out.println("账户异常，扣减失败");
        throw new RuntimeException("账户异常，扣减失败");
    }
}
