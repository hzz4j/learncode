package org.hzz.service.impl;

import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.hzz.config.DataSourceKey;
import org.hzz.config.DynamicDataSourceContextHolder;
import org.hzz.entity.Storage;
import org.hzz.mapper.StorageMapper;
import org.hzz.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageMapper storageMapper;

    @Override
    @Transactional
    public void deduct(String commodityCode, int count) {
        log.info("=============扣减库存=================");
        //切换数据源
        DynamicDataSourceContextHolder.setDataSourceKey(DataSourceKey.STORAGE);
        log.info("当前 XID: {}", RootContext.getXID());
        // 检查库存
        checkStock(commodityCode,count);

        log.info("开始扣减 {} 库存", commodityCode);
        Integer record = storageMapper.reduceStorage(commodityCode,count);
        log.info("扣减 {} 库存结果:{}", commodityCode, record > 0 ? "操作成功" : "扣减库存失败");
    }

    private void checkStock(String commodityCode, int count){

        log.info("检查 {} 库存", commodityCode);
        Storage storage = storageMapper.findByCommodityCode(commodityCode);
        if(storage == null){
            throw new RuntimeException("库存不存在");
        }
        if (storage.getCount() < count) {
            log.warn("{} 库存不足，当前库存:{}", commodityCode, count);
            throw new RuntimeException("库存不足");
        }
    }
}
