package org.hzz.shutdown;

import lombok.extern.slf4j.Slf4j;
import org.hzz.promise.Promise;

import java.util.Scanner;

@Slf4j
public class TestShutdownDemo {
    public static void main(String[] args) throws InterruptedException {
        EventGroup group = new EventGroup(3);
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入任意字符，关闭线程组");
        scanner.next();
        Promise<?> promise = group.shutdownGracefully();
        promise.addListener(future -> {
            log.info("线程组关闭成功");
        });
        promise.sync();
        log.info("主线程退出");
    }
}
