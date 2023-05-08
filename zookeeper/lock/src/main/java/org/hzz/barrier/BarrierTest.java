package org.hzz.barrier;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class BarrierTest {
    private static final AtomicInteger counter = new AtomicInteger(0);
    private static final Executor excutor = command -> {
        Thread thread = new Thread(command, "thread-" + counter.incrementAndGet());
        thread.setDaemon(false);
        thread.start();
    };

    public static void main(String[] args) throws InterruptedException {
        IZKBarrier zkBarrier = new ZkBarrier(3);
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            TimeUnit.SECONDS.sleep(random.nextInt(10));
            excutor.execute(() -> {
                log.info("线程 {} 到达", Thread.currentThread().getName());
                zkBarrier.doWait();
                log.info("线程 {} 通过", Thread.currentThread().getName());
            });
        }
    }
}
/**
 * 15:55:52.542 BarrierTest [thread-1] : 线程 thread-1 到达
 * 15:55:52.557 ZkBarrier [thread-1] : 创建子节点 /barrier-412dd65d-41c7-45c1-9bab-c0b4b919311e/children-0000000000
 * 15:55:52.573 ZkBarrier [thread-1] : 等待其他线程
 * 15:55:57.552 BarrierTest [thread-2] : 线程 thread-2 到达
 * 15:55:57.571 ZkBarrier [hzz-thread-EventThread] : 监听到容器子节点变化
 * 15:55:57.571 ZkBarrier [thread-2] : 创建子节点 /barrier-412dd65d-41c7-45c1-9bab-c0b4b919311e/children-0000000001
 * 15:55:57.580 ZkBarrier [thread-2] : 等待其他线程
 * 15:56:05.562 BarrierTest [thread-3] : 线程 thread-3 到达
 * 15:56:05.570 ZkBarrier [hzz-thread-EventThread] : 监听到容器子节点变化
 * 15:56:05.570 ZkBarrier [thread-3] : 创建子节点 /barrier-412dd65d-41c7-45c1-9bab-c0b4b919311e/children-0000000002
 * 15:56:05.572 ZkBarrier [thread-3] : 其他线程已经到达
 * 15:56:05.573 ZkBarrier [hzz-thread-EventThread] : 监听到容器子节点变化
 * 15:56:05.573 ZkBarrier [thread-2] : 其他线程已经到达
 * 15:56:05.573 ZkBarrier [thread-1] : 其他线程已经到达
 * 15:56:05.573 BarrierTest [thread-2] : 线程 thread-2 通过
 * 15:56:05.573 BarrierTest [thread-1] : 线程 thread-1 通过
 * 15:56:05.589 ZkBarrier [hzz-thread-EventThread] : 监听到容器子节点变化
 * 15:56:05.599 ZkBarrier [hzz-thread-EventThread] : 监听到容器子节点变化
 * 15:56:05.602 ZkBarrier [thread-3] : 清除完节点成功
 * 15:56:05.602 BarrierTest [thread-3] : 线程 thread-3 通过
 * 15:56:05.602 ZkBarrier [hzz-thread-EventThread] : 节点/barrier-412dd65d-41c7-45c1-9bab-c0b4b919311e已经被删除
 */
