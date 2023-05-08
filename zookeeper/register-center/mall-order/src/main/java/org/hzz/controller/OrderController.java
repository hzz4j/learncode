package org.hzz.controller;

import org.hzz.entity.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/order")
public class OrderController {

    @RequestMapping("/findOrderByUserId/{userId}")
    public Result findOrderByUserId(@PathVariable("userId") Integer userId) {
        List<Map<String, Object>> orders = new ArrayList<>();
        orders.add(generateOrder(1, 100.0, "电脑"));
        orders.add(generateOrder(2, 80.0, "水杯"));
        orders.add(generateOrder(3, 1900.0, "苹果电脑"));
        return Result.ok()
                .put("userId", userId)
                .put("orders",orders);
    }

    private Map<String,Object> generateOrder(Integer goodsId,Double price,String orderName) {
        return Stream.of(new Object[][] {
                { "goods_id", goodsId},
                {"order_name", orderName },
                { "price", price },
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Object) data[1]));
    }
}
