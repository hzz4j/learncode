package org.hzz.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface StorageService {
    /**
     * Try: 库存-扣减数量，冻结库存+扣减数量
     *
     * 定义两阶段提交，在try阶段通过@TwoPhaseBusinessAction注解定义了分支事务的 resourceId，commit和 cancel 方法
     *  name = 该tcc的bean名称,全局唯一
     *  commitMethod = commit 为二阶段确认方法
     *  rollbackMethod = rollback 为二阶段取消方法
     *  BusinessActionContextParameter注解 传递参数到二阶段中
     *
     * @param commodityCode 商品编号
     * @param count 扣减数量
     * @return
     */
    @TwoPhaseBusinessAction(name = "deduct", commitMethod = "commit", rollbackMethod = "rollback", useTCCFence = true)
    boolean deduct(@BusinessActionContextParameter(paramName = "commodityCode") String commodityCode,
                   @BusinessActionContextParameter(paramName = "count") int count);

    /**
     *
     * Confirm: 冻结库存-扣减数量
     * 二阶段确认方法可以另命名，但要保证与commitMethod一致
     * context可以传递try方法的参数
     *
     * @param actionContext
     * @return
     */
    boolean commit(BusinessActionContext actionContext);

    /**
     * Cancel: 库存+扣减数量，冻结库存-扣减数量
     * 二阶段取消方法可以另命名，但要保证与rollbackMethod一致
     *
     * @param actionContext
     * @return
     */
    boolean rollback(BusinessActionContext actionContext);
}
