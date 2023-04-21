package org.hzz.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface AccountService {
    /**
     * 用户账户扣款
     *
     * 定义两阶段提交，在try阶段通过@TwoPhaseBusinessAction注解定义了分支事务的 resourceId，commit和 cancel 方法
     *  name = 该tcc的bean名称,全局唯一
     *  commitMethod = commit 为二阶段确认方法
     *  rollbackMethod = rollback 为二阶段取消方法
     *
     * @param userId
     * @param money 从用户账户中扣除的金额
     * @return
     */
    @TwoPhaseBusinessAction(name = "debit", commitMethod = "commit", rollbackMethod = "rollback", useTCCFence = true)
    boolean debit(@BusinessActionContextParameter(paramName = "userId") String userId,
                  @BusinessActionContextParameter(paramName = "money") int money);

    /**
     * 提交事务，二阶段确认方法可以另命名，但要保证与commitMethod一致
     * context可以传递try方法的参数
     *
     * @param actionContext
     * @return
     */
    boolean commit(BusinessActionContext actionContext);

    /**
     * 回滚事务，二阶段取消方法可以另命名，但要保证与rollbackMethod一致
     *
     * @param actionContext
     * @return
     */
    boolean rollback(BusinessActionContext actionContext);
}
