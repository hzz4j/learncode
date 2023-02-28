package org.hzz.abstractfactory.db.oracle;

import org.hzz.abstractfactory.command.ICommand;
import org.hzz.abstractfactory.component.IDBComponent;
import org.hzz.abstractfactory.connection.IConnection;

public class OracleComponent implements IDBComponent {
    @Override
    public IConnection getConnection() {
        return new OracleConnection();
    }

    @Override
    public ICommand getCommand() {
        return new OracleCommand();
    }
}
