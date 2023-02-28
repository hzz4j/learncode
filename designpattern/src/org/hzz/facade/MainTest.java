package org.hzz.facade;

import org.hzz.facade.client.Client1;
import org.hzz.facade.client.Client2;

public class MainTest {
    public static void main(String[] args) {
        Client1 client1 = new Client1();
        Client2 client2 = new Client2();
        client1.doSomething1();
        client2.doSomething2();
    }
}
