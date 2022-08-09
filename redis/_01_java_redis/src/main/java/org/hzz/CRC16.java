package org.hzz;

import redis.clients.util.JedisClusterCRC16;

/**
 * Redis分片算法
 */
public class CRC16 {
    public static void main(String[] args) {
        String key="user:q10viking";
        // 4132
        System.out.println(JedisClusterCRC16.getCRC16(key) % 16384);
    }
}
