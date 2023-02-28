package org.hzz.abstractfactory.db.oracle;

import org.hzz.abstractfactory.connection.IConnection;

public class OracleConnection implements IConnection {
    @Override
    public void connection() {
        System.out.println("oracle: connect.");
    }
}
