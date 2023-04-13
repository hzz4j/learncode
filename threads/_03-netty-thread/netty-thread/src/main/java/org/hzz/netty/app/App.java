package org.hzz.netty.app;

import org.hzz.netty.NioEventLoop;
import org.hzz.netty.NioEventLoopGroup;
import org.hzz.netty.ThreadExecutorMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);
    public static void main(String[] args) {
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(2);
        Scanner scanner = new Scanner(System.in);
        while(true){
            String msg = scanner.nextLine();
            nioEventLoopGroup.submit(makeTask(msg));
        }
    }

    public static Runnable makeTask(String msg){
        return () -> {
            log.info("msg:{}",msg);
            // 验证ThreadExecutorMap的正确性
            log.info(String.valueOf((NioEventLoop) ThreadExecutorMap.currentEventExecutor()));
        };
    }
}
