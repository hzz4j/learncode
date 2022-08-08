package org.hzz;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class SimpleJedis {
    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);
        // timeout，这里既是连接超时又是读写超时，从Jedis 2.8开始有区分connectionTimeout和soTimeout的构造函数
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.187.135", 6379, 3000, null);
        //从redis连接池里拿出一个连接执行命令
        Jedis jedis = jedisPool.getResource();

        String set = jedis.set("start", "Hello World");
        System.out.println(set);
        System.out.println(jedis.get("start"));
    }
}
/**
 * OK
 * Hello World
 */