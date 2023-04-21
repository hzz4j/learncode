package org.hzz.controller;

import lombok.extern.slf4j.Slf4j;
import org.hzz.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    public Boolean debit(String xid,String userId, int money) throws Exception {
        // 用户账户扣款
        return accountService.debit(userId, money);
    }
}
