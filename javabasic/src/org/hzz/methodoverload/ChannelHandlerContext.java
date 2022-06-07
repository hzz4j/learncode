package org.hzz.methodoverload;

public class ChannelHandlerContext implements Channel{
    @Override
    public void run(String name) {
        System.out.println("run: " + name);
    }
}
