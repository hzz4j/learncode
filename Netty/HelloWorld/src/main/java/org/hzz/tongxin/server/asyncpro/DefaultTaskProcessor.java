package org.hzz.tongxin.server.asyncpro;

import org.hzz.tongxin.vo.MyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultTaskProcessor implements ITaskProcessor{
    private static final Logger LOG = LoggerFactory.getLogger(DefaultTaskProcessor.class);
    @Override
    public Runnable execAsyncTask(MyMessage msg) {
        return createTask(msg);
    }

    private Runnable createTask(MyMessage msg){
        return ()->{
            LOG.info("DefaultTaskProcessor模拟任务处理："+msg.getBody());
        };
    }
}
