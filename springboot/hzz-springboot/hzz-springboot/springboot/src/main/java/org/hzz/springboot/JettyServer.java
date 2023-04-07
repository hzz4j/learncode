package org.hzz.springboot;

public class JettyServer implements WebServer {
    @Override
    public void start() {
        System.out.println("JettyServer start");
    }
}
