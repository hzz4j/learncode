package org.hzz.tongxin.server.asyncpro;

import org.hzz.tongxin.vo.MyMessage;

public interface ITaskProcessor {
    Runnable execAsyncTask(MyMessage msg);
}
