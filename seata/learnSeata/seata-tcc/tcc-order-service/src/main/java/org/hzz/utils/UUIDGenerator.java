package org.hzz.utils;

import io.seata.common.util.IdWorker;

public class UUIDGenerator {
    private static volatile IdWorker idWorker;

    /**
     * generate UUID using snowflake algorithm
     * @return UUID
     */
    public static long generateUUID() {
        if (idWorker == null) {
            synchronized (UUIDGenerator.class) {
                if (idWorker == null) {
                    init(null);
                }
            }
        }
        return idWorker.nextId();
    }

    /**
     * init IdWorker
     * @param serverNode the server node id, consider as machine id in snowflake
     */
    public static void init(Long serverNode) {
        idWorker = new IdWorker(serverNode);
    }
}
