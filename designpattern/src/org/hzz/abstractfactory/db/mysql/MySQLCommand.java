package org.hzz.abstractfactory.db.mysql;

import org.hzz.abstractfactory.command.ICommand;

public class MySQLCommand implements ICommand {
    @Override
    public void command() {
        System.out.println("mysql: command.");
    }
}
