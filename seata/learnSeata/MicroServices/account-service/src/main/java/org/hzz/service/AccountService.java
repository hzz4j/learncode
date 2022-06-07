package org.hzz.service;

public interface AccountService {
    /**
     * 用户账户扣款
     * @param userId
     * @param money 从用户账户中扣除的金额
     */
    void debit(String userId, int money) ;
}
