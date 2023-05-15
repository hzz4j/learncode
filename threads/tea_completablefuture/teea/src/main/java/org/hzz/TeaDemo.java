package org.hzz;

import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
public class TeaDemo {
    private static Logger LOG = LoggerFactory.getLogger(TeaDemo.class);
    public static void main(String[] args) {
        LOG.info("=================泡茶START===========================");
        //任务1：洗水壶->烧开水
        CompletableFuture<Void> futureTask1 = CompletableFuture.runAsync(() -> {
            LOG.info("T1:洗水壶...");
            Utils.sleepSeconds(1);
        }).thenRun(() -> {
            LOG.info("T1:烧开水...");
            Utils.sleepSeconds(15);
        });

        //任务2：洗茶壶->洗茶杯->拿茶叶
        CompletableFuture<String> futureTask2 = CompletableFuture.runAsync(() -> {
            LOG.info("T2:洗茶壶...");
            Utils.sleepSeconds(1);
        }).thenRun(() -> {
            LOG.info("T2:洗茶杯...");
            Utils.sleepSeconds(2);
        }).thenCompose((Void) -> CompletableFuture.supplyAsync(() -> { // 返回一个结果
            LOG.info("T2:拿茶叶...");
            Utils.sleepSeconds(1);
            return "龙井";
        }));

        //任务3：任务1和任务2完成后执行：泡茶
        CompletableFuture<Void> teaTask = futureTask1.thenCombine(futureTask2, (Void, tf) -> {
            LOG.info("T3:拿到茶叶:" + tf);
            LOG.info("T3:泡茶...");
            LOG.info("T3: 上茶...");
            return tf;
        }).thenAccept(result -> {
            LOG.info("Q10Viking 喝茶..." + result);
        });

        // 等待任务3执行完成
        teaTask.join();
        LOG.info("=================泡茶END===========================");
    }
}