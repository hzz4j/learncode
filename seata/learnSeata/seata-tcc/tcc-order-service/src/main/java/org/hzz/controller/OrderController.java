package org.hzz.controller;

import org.hzz.entity.Order;
import org.hzz.service.BussinessService;
import org.hzz.vo.OrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.hzz.vo.ResultVo;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    private BussinessService bussinessService;

    @PostMapping("/create")
    public ResultVo createOrder(@RequestBody OrderVo orderVo){
        log.info("收到下单请求,用户:{}, 商品编号:{}", orderVo.getUserId(), orderVo.getCommodityCode());
        Order order = bussinessService.saveOrder(orderVo);
        return ResultVo.ok().put("order",order);
    }

}
