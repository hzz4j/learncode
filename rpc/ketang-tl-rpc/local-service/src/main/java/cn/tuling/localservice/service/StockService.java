package cn.tuling.localservice.service;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：变动库存服务接口
 */
public interface StockService {
    /*增加库存*/
    void addStock(String goodsId, int addAmout);
    /*扣减库存*/
    void deduceStock(String goodsId, int deduceAmout);
}
