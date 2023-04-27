package org.hzz.client;

import org.apache.zookeeper.KeeperException;
import org.hzz.Selection;

import java.io.IOException;

public class Client3 {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        Selection selection = new Selection("192.168.135.135", 8081, 3);
        selection.selection();
        Runtime.getRuntime().addShutdownHook(new Thread(()->selection.close()));
        while(true);
    }
}
