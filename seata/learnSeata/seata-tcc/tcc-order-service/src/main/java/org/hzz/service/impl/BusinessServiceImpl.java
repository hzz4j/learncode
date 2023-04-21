package org.hzz.service.impl;

import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.hzz.entity.Order;
import org.hzz.feign.AccountFeignService;
import org.hzz.feign.StorageFeignService;
import org.hzz.service.BussinessService;
import org.hzz.service.OrderService;
import org.hzz.utils.UUIDGenerator;
import org.hzz.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BusinessServiceImpl implements BussinessService {

    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountFeignService accountFeignService;

    @Autowired
    private StorageFeignService storageFeignService;


    @Override
    @GlobalTransactional(name = "createOrder",rollbackFor = Exception.class)
    public Order saveOrder(OrderVo orderVo) {
        log.info("=============用户下单=================");
        log.info("当前 XID: {}", RootContext.getXID());

        //获取全局唯一订单号  测试使用
        Long orderId = UUIDGenerator.generateUUID();

        //阶段一： 创建订单
        Order order = orderService.prepareSaveOrder(orderVo,orderId);

        //扣减库存
        storageFeignService.deduct(orderVo.getCommodityCode(), orderVo.getCount());
        //扣减余额
        accountFeignService.debit(orderVo.getUserId(), orderVo.getMoney());

        return order;
    }
}
