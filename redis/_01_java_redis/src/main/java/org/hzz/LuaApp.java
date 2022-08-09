package org.hzz;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Arrays;

/***
 * Lua脚本测试
 */
public class LuaApp {
    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);
        // timeout，这里既是连接超时又是读写超时，从Jedis 2.8开始有区分connectionTimeout和soTimeout的构造函数
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.187.135", 6379, 3000, null);
        //从redis连接池里拿出一个连接执行命令
        Jedis jedis = jedisPool.getResource();

        //lua脚本命令执行方式：redis-cli --eval /tmp/test.lua , 10
        jedis.set("product_stock_10016", "15");  //初始化商品10016的库存
        String script = " local count = redis.call('get', KEYS[1]) " +
                " local a = tonumber(count) " +
                " local b = tonumber(ARGV[1]) " +
                " if a >= b then " +
                "   redis.call('set', KEYS[1], a-b) " +
                //模拟语法报错回滚操作
                // "   bb == 0 " +
                "   return 1 " +
                " end " +
                " return 0 ";
        // 返回的就是Lua脚本返回的return
        Object obj = jedis.eval(script, Arrays.asList("product_stock_10016"), Arrays.asList("10"));
        System.out.println(obj); // 1
        System.out.println(jedis.get("product_stock_10016")); // 5
    }
}
