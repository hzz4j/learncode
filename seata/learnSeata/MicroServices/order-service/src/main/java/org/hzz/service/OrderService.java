package org.hzz.service;

import io.seata.core.exception.TransactionException;
import org.hzz.entity.Order;
import org.hzz.vo.OrderVo;

public interface OrderService {
    /**
     * 保存订单
     */
    Order saveOrder(OrderVo orderVo) throws TransactionException;
}
