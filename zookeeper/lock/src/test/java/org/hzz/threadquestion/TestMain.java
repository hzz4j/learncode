package org.hzz.threadquestion;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;

@Slf4j
public class TestMain {
    public static void main(String[] args) {
        log.info("main start");
        MyBarrier myBarrier = new MyBarrier();
        log.info("main exist");

    }
}
