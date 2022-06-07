package org.hzz.service.impl;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.hzz.entity.Account;
import org.hzz.mapper.AccountMapper;
import org.hzz.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    /**
     * 扣减用户金额
     * @param userId
     * @param money
     */
    @Transactional
    @Override
    public void debit(String userId, int money){
        log.info("=============用户账户扣款=================");
        log.info("当前 XID: {}", RootContext.getXID());

        checkBalance(userId, money);

        log.info("开始扣减用户 {} 余额", userId);
        Integer record = accountMapper.reduceBalance(userId,money);

//        if (ERROR_USER_ID.equals(userId)) {
//            // 模拟异常
//            throw new RuntimeException("account branch exception");
//        }
        log.info("扣减用户 {} 余额结果:{}", userId, record > 0 ? "操作成功" : "扣减余额失败");
    }

    private void checkBalance(String userId, int money){
        log.info("检查用户 {} 余额", userId);
        Account account = accountMapper.selectByUserId(userId);

        if (account.getMoney() < money) {
            log.warn("用户 {} 余额不足，当前余额:{}", userId, account.getMoney());
            throw new RuntimeException("余额不足");
        }

    }
}
