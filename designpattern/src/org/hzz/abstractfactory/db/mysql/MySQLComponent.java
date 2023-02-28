package org.hzz.abstractfactory.db.mysql;

import org.hzz.abstractfactory.command.ICommand;
import org.hzz.abstractfactory.component.IDBComponent;
import org.hzz.abstractfactory.connection.IConnection;

public class MySQLComponent implements IDBComponent {
    @Override
    public IConnection getConnection() {
        return new MySQLConnection();
    }

    @Override
    public ICommand getCommand() {
        return new MySQLCommand();
    }
}
