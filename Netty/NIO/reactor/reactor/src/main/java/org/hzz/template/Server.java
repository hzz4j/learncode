package org.hzz.template;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException {
        Reactor reactor = new Reactor(8080);
        new Thread(reactor,"Thread-0").start();
//        new Thread(reactor,"Thread-1").start();
    }
}
