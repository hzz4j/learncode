package org.hzz;

import io.netty.util.NettyRuntime;

public class TestMain {
    public static void main(String[] args) {
        int availableProcessors = NettyRuntime.availableProcessors() * 2;
        System.out.println(availableProcessors);
    }
}
