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
        // 增大线程
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(2);

        for (int i = 0; i < 10; i++) {
            String msg = "msg" + i;
            nioEventLoopGroup.submit(makeTask(msg));
        }

        // 增加线程
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入要增加的线程数");
        String number = scanner.nextLine();
        int n = Integer.parseInt(number);
        nioEventLoopGroup.addNThreads(n);
        System.out.println("请输入任意字符继续");
        String next = scanner.next();
        for (int i = 0; i < 100; i++) {
            String msg = "msg" + i;
            nioEventLoopGroup.submit(makeTask(msg));
        }
    }

    public static Runnable makeTask(String msg){
        return new Task(msg);
    }

    static class Task implements Runnable{
        private String msg;
        public Task(String msg){
            this.msg = msg;
        }
        @Override
        public void run() {
            // 通过ThreadExecutorMap获取当前线程的EventExecutor
            log.info("{},msg:{}",ThreadExecutorMap.currentEventExecutor(),msg);
        }

        @Override
        public String toString() {
            return "Task{" +
                    "msg='" + msg + '\'' +
                    '}';
        }
    }
}
