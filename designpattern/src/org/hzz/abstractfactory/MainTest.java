package org.hzz.abstractfactory;

import org.hzz.abstractfactory.command.ICommand;
import org.hzz.abstractfactory.component.IDBComponent;
import org.hzz.abstractfactory.connection.IConnection;
import org.hzz.abstractfactory.db.mysql.MySQLComponent;
import org.hzz.abstractfactory.db.oracle.OracleComponent;

public class MainTest {
    public static void main(String[] args) {
//        IDBComponent db = new OracleComponent();
        IDBComponent db = new MySQLComponent();
        IConnection connection = db.getConnection();
        ICommand command = db.getCommand();
        connection.connection();
        command.command();
    }
}
