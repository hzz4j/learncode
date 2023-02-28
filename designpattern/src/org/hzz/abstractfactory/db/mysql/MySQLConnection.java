package org.hzz.abstractfactory.db.mysql;

import org.hzz.abstractfactory.connection.IConnection;

public class MySQLConnection implements IConnection {
    @Override
    public void connection() {
        System.out.println("mysql: connect.");
    }
}
