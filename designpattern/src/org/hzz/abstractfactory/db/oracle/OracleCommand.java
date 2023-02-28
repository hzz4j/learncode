package org.hzz.abstractfactory.db.oracle;

import org.hzz.abstractfactory.command.ICommand;

public class OracleCommand implements ICommand {
    @Override
    public void command() {
        System.out.println("oracle: command.");
    }
}
