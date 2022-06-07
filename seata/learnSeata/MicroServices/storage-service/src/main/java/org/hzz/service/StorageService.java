package org.hzz.service;

public interface StorageService {
    /**
     * 扣减库存
     * @param commodityCode 商品编号
     * @param count 扣除数量
     */
    void deduct(String commodityCode, int count);
}
