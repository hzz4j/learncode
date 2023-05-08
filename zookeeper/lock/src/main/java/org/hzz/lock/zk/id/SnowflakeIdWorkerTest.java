package org.hzz.lock.zk.id;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;

/**
 * 测试使用zk生产雪花算法id
 */
@Slf4j
public class SnowflakeIdWorkerTest {
    public static void main(String[] args) {
        IDMaker idMaker = new IDMaker();
        Executor executor = command -> new Thread(command).start();
        // 3个线程，每个线程生成3个id
        for(int i = 0;i < 3;i++){
            executor.execute(() -> {
                String workIDStr = idMaker.makeId();
                SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(Long.parseLong(workIDStr));
                for (int j = 0; j < 3; j++) {
                    long id = snowflakeIdWorker.nextId();
                    log.info("workdId:{} 生产 id:{}",workIDStr,id);
                }
            });
        }
    }
}
/**
 * 21:29:05.332 IDMaker [Thread-1] : createEphemeralSequential: /idmaker/id-0000000000
 * 21:29:05.334 SnowflakeIdWorkerTest [Thread-1] : workdId:0000000000 生产 id:7057707296444383232
 * 21:29:05.334 SnowflakeIdWorkerTest [Thread-1] : workdId:0000000000 生产 id:7057707296448577536
 * 21:29:05.334 SnowflakeIdWorkerTest [Thread-1] : workdId:0000000000 生产 id:7057707296448577537
 * 21:29:05.341 IDMaker [Thread-2] : createEphemeralSequential: /idmaker/id-0000000001
 * 21:29:05.345 SnowflakeIdWorkerTest [Thread-2] : workdId:0000000001 生产 id:7057707296477941760
 * 21:29:05.345 SnowflakeIdWorkerTest [Thread-2] : workdId:0000000001 生产 id:7057707296494718976
 * 21:29:05.345 SnowflakeIdWorkerTest [Thread-2] : workdId:0000000001 生产 id:7057707296494718977
 * 21:29:05.349 IDMaker [Thread-3] : createEphemeralSequential: /idmaker/id-0000000002
 * 21:29:05.350 SnowflakeIdWorkerTest [Thread-3] : workdId:0000000002 生产 id:7057707296515694592
 * 21:29:05.350 SnowflakeIdWorkerTest [Thread-3] : workdId:0000000002 生产 id:7057707296515694593
 * 21:29:05.350 SnowflakeIdWorkerTest [Thread-3] : workdId:0000000002 生产 id:7057707296515694594
 */




