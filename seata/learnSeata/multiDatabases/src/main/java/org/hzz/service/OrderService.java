package org.hzz.service;

import org.hzz.entity.Order;
import org.hzz.vo.OrderVo;

public interface OrderService {
    Order saveOrder(OrderVo orderVo);
}
