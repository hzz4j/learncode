package org.hzz.tl.db;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 数据连接池实现
 */
public class DBPool {
    /**容器，存放连接*/
    private static LinkedList<Connection> pool = new LinkedList<>();


    public Connection fetchConnection(long mills){

        return null;
    }

}
