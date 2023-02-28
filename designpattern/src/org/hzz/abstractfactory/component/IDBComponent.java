package org.hzz.abstractfactory.component;

import org.hzz.abstractfactory.command.ICommand;
import org.hzz.abstractfactory.connection.IConnection;

public interface IDBComponent {
    IConnection getConnection();
    ICommand getCommand();
}
