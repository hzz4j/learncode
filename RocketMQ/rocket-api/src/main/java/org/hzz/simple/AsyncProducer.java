package org.hzz.simple;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.hzz.consts.Addr;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;

/**
 * 异步发送
 */
public class AsyncProducer {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("basic_async_producer_group");
        producer.setNamesrvAddr(Addr.NAME_SERVER_ADDR);
        producer.start();

        producer.setRetryTimesWhenSendAsyncFailed(3);

        int msgCount = 5;
        final CountDownLatch countDownLatch = new CountDownLatch(msgCount);
        for(int i=0;i<msgCount;i++){
            try {
                final int index = i;
                Message msg = new Message("basic_async_topic",
                        "hzz_tag",
                        "OrderID188",
                        "Hello world".getBytes(RemotingHelper.DEFAULT_CHARSET));

                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable e) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                    }
                });
                System.out.println("finish send.");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        countDownLatch.await();
        Thread.sleep(1000_000);
        producer.shutdown();
    }
}
