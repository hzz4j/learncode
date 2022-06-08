package org.hzz.transaction;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

public class TransactionListenerImpl implements TransactionListener {
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        String threadName = Thread.currentThread().getName();
        String tags = msg.getTags();
        if(StringUtils.contains(tags,"TagA")){
            return LocalTransactionState.COMMIT_MESSAGE;
        }else if(StringUtils.contains(tags,"TagB")){
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }else{
            return LocalTransactionState.UNKNOW;
        }
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        String threadName = Thread.currentThread().getName();
        String tags = msg.getTags();
        if(StringUtils.contains(tags,"TagC")){
            System.out.println(threadName+" TagC COMMIT_MESSAGE");
            return LocalTransactionState.COMMIT_MESSAGE;
        }else if(StringUtils.contains(tags,"TagD")){
            System.out.println(threadName+" TagD ROLLBACK_MESSAGE");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }else{
            System.out.println(threadName+" "+tags+" UNKNOW");
            return LocalTransactionState.UNKNOW;
        }
    }
}
