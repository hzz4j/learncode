package org.hzz;

import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class BloomFilterApp {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.187.135:6379");

        RedissonClient redissonClient = Redisson.create(config);
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter("name_list");
        // 初始化布隆过滤器：预计元素为255000000L,误差率为3%,根据这两个参数会计算出底层的bit数组大小
        bloomFilter.tryInit(255000000L, 0.03);
        bloomFilter.add("q10viking");
        bloomFilter.add("静默");
        System.out.println(bloomFilter.contains("q10viking"));
        System.out.println(bloomFilter.contains("静默"));
        System.out.println(bloomFilter.contains("hzz"));
    }
}
/**
 * true
 * true
 * false
 */