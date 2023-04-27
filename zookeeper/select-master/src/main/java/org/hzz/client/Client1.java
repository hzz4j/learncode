package org.hzz.client;

import org.apache.zookeeper.KeeperException;
import org.hzz.Selection;

import java.io.IOException;

public class Client1 {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        Selection selection = new Selection("192.168.135.130", 8081, 1);
        selection.selection();
        Runtime.getRuntime().addShutdownHook(new Thread(()->selection.close()));
        while(true);
    }
}
