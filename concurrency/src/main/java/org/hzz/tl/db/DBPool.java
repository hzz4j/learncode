package org.hzz.tl.db;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 数据连接池简单实现
 */
public class DBPool {
    /**容器，存放连接*/
    private static LinkedList<Connection> pool = new LinkedList<>();

    public DBPool(int initialSize){
        if(initialSize > 0){
            for (int i=0; i<initialSize; i++){
                pool.add(SqlConnectionImpl.fetchConnection());
            }
        }
    }

    /** 获取连接  */
    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool){
            if(mills <= 0){
                while(pool.isEmpty()){
                    pool.wait();
                }
                return pool.removeFirst();
            }else{
                // 超时时刻
                long future = System.currentTimeMillis() + mills;
                long remaining = mills;
                while(pool.isEmpty() && remaining > 0){
                    pool.wait(mills);
                    // 被唤醒重新计算以下剩余时间
                    remaining = future - System.currentTimeMillis();
                }
                return pool.removeFirst();
            }
        }
    }

    /**释放连接*/
    public void releaseConnection(Connection connection){
        if(connection != null){
            synchronized (pool){
                pool.addLast(connection);
                // java中任何一个对象都有成为锁的潜质
                pool.notify();
            }
        }
    }
}
