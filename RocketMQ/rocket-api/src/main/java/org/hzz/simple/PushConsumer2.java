package org.hzz.simple;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.hzz.consts.Addr;

import java.util.List;

public class PushConsumer2 {
    public static void main(String[] args) throws MQClientException {
        consumer1();
    }

    public static void consumer1() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("basic_sync_consumer_group_1");
        consumer.setNamesrvAddr(Addr.NAME_SERVER_ADDR);
        consumer.subscribe("basic_sync_topic","*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("Consumer1->%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.println("Conumser1 started.");
    }
}
