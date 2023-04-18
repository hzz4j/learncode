package cn.tuling.localservice.service.impl;

import cn.tuling.localservice.service.StockService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：库存数量变动服务的实现
 */
@Service
public class StockServiceImpl implements StockService {

	//存放库存数据
    private static ConcurrentHashMap<String,Integer> goodsData =
            new ConcurrentHashMap<String, Integer>();

    static {
        goodsData.put("A001",1000);
        goodsData.put("B002",2000);
        goodsData.put("C003",3000);
        goodsData.put("D004",4000);
    }

    @Override
    public synchronized void addStock(String goodsId, int addAmout) {
        System.out.println("+++++++++++++++++增加商品："+goodsId+"的库存,数量为："+addAmout);
        int amount = goodsData.get(goodsId)+addAmout;
        goodsData.put(goodsId,amount);
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("+++++++++++++++++商品："+goodsId+"的库存,数量变为："+amount);
    }

    @Override
    public synchronized void deduceStock(String goodsId, int deduceAmout) {
        System.out.println("-------------------减少商品："+goodsId+"的库存,数量为："+ deduceAmout);
        int amount = goodsData.get(goodsId)- deduceAmout;
        goodsData.put(goodsId,amount);
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-------------------商品："+goodsId+"的库存,数量变为："+amount);
    }
}
