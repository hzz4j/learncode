package org.hzz.service;

import org.hzz.entity.Order;
import org.hzz.vo.OrderVo;

public interface BussinessService {
    /**
     * 保存订单
     */
    Order saveOrder(OrderVo orderVo) ;
}
