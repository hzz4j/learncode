package org.hzz.factory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestDefaultFactory {
    public static void main(String[] args) {
        DefaultThreadFactory threadFactory = new DefaultThreadFactory(TestDefaultFactory.class);
        threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                log.info("hello");
            }
        }).start();
    }
}
/**
 * 11:37:02.741 [testDefaultFactory-1-thread-1] INFO org.hzz.factory.TestDefaultFactory - hello
 */