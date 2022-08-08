package org.hzz;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试哨兵架构
 */
public class SentinelApp {
    public static void main(String[] args) {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(20);
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMinIdle(5);

        // sentinel monitor mymaster 192.168.187.135 6379 2   # mymaster这个名字随便取，客户端访问时会用到
        String masterName = "mymaster";
        Set<String> sentinels = new HashSet<String>();
        sentinels.add(new HostAndPort("192.168.187.135",26379).toString());
        sentinels.add(new HostAndPort("192.168.187.135",26380).toString());
        sentinels.add(new HostAndPort("192.168.187.135",26381).toString());
        //JedisSentinelPool其实本质跟JedisPool类似，都是与redis主节点建立的连接池
        //⭐JedisSentinelPool并不是说与sentinel建立的连接池，而是通过sentinel发现redis主节点并与其建立连接⭐
        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool(masterName, sentinels, jedisPoolConfig, 3000, null);
        Jedis jedis = null;
        try{
            jedis = jedisSentinelPool.getResource();
            String res = jedis.set("test:greeting", "Hello Sentinel by 静默");
            System.out.println(res);
            System.out.println(jedis.get("test:greeting"));
        }catch (Exception e){
            // ...
        }finally {
            if(jedis != null){
                //注意这里不是关闭连接，在JedisPool模式下，Jedis会被归还给资源池。
                jedis.close();
            }
        }
    }
}
/**
 * 八月 09, 2022 1:06:05 上午 redis.clients.jedis.JedisSentinelPool initSentinels
 * 信息: Trying to find master from available Sentinels...
 * 八月 09, 2022 1:06:05 上午 redis.clients.jedis.JedisSentinelPool initSentinels
 * 信息: Redis master running at 192.168.187.135:6381, starting Sentinel listeners...
 * 八月 09, 2022 1:06:05 上午 redis.clients.jedis.JedisSentinelPool initPool
 * 信息: Created JedisPool to master at 192.168.187.135:6381
 * OK
 * Hello Sentinel by 静默
 */