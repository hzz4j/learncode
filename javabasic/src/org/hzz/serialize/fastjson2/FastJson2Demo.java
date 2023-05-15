package org.hzz.serialize.fastjson2;

import com.alibaba.fastjson2.JSON;

public class FastJson2Demo {
    public static void main(String[] args) {
        Goods goods = new Goods("iPhone", null);
        System.out.println(goods);

        // 序列化
        String goodsJson = JSON.toJSONString(goods);
        System.out.println(goodsJson);
        // 反序列化
        Goods goods1 = JSON.parseObject(goodsJson, Goods.class);
        System.out.println(goods1);
    }
}
/**
 * Goods(name=iPhone, price=null)
 * {"name":"iPhone"}
 * Goods(name=iPhone, price=null)
 */