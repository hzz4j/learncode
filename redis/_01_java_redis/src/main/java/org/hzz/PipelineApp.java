package org.hzz;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * 管道例子
 */
public class PipelineApp {
    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);
        // timeout，这里既是连接超时又是读写超时，从Jedis 2.8开始有区分connectionTimeout和soTimeout的构造函数
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.187.135", 6379, 3000, null);
        //从redis连接池里拿出一个连接执行命令
        Jedis jedis = jedisPool.getResource();

        //管道的命令执行方式：cat redis.txt | redis-cli -h 127.0.0.1 -a password - p 6379 --pipe
        Pipeline pipeline = jedis.pipelined();
        for (int i = 0; i < 10; i++) {
            pipeline.incr("pipelineKey");
            pipeline.set("test::q10viking("+i+")","No."+i);
            //模拟管道报错
            //pl.setbit("error", -1, true);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        System.out.println(results);

    }
}
/**
 * [1, OK, 2, OK, 3, OK, 4, OK, 5, OK, 6, OK, 7, OK, 8, OK, 9, OK, 10, OK]
 */
