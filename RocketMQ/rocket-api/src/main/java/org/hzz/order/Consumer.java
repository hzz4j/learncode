package org.hzz.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.hzz.consts.Addr;

import java.util.List;

public class Consumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order_consumer_group");
        consumer.setNamesrvAddr(Addr.NAME_SERVER_ADDR);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

        consumer.subscribe("order_topic", "*");

        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                // 有一个自动提交，如果设置为false,当consumer重启时会继续消费到
                context.setAutoCommit(true);
                for(MessageExt msg:msgs){
                    System.out.println(Thread.currentThread().getName()+"->收到消息内容 "+new String(msg.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });


        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
/** 局部有序性
 ConsumeMessageThread_3->收到消息内容 order_0 step 0
 ConsumeMessageThread_2->收到消息内容 order_3 step 0
 ConsumeMessageThread_1->收到消息内容 order_4 step 0
 ConsumeMessageThread_1->收到消息内容 order_4 step 1
 ConsumeMessageThread_3->收到消息内容 order_0 step 1
 ConsumeMessageThread_2->收到消息内容 order_3 step 1
 ConsumeMessageThread_3->收到消息内容 order_0 step 2
 ConsumeMessageThread_1->收到消息内容 order_4 step 2
 ConsumeMessageThread_3->收到消息内容 order_0 step 3
 ConsumeMessageThread_2->收到消息内容 order_3 step 2
 ConsumeMessageThread_3->收到消息内容 order_0 step 4
 ConsumeMessageThread_1->收到消息内容 order_4 step 3
 ConsumeMessageThread_3->收到消息内容 order_0 step 5
 ConsumeMessageThread_1->收到消息内容 order_4 step 4
 ConsumeMessageThread_2->收到消息内容 order_3 step 3
 ConsumeMessageThread_1->收到消息内容 order_4 step 5
 ConsumeMessageThread_2->收到消息内容 order_3 step 4
 ConsumeMessageThread_2->收到消息内容 order_3 step 5
 ConsumeMessageThread_4->收到消息内容 order_1 step 0
 ConsumeMessageThread_4->收到消息内容 order_1 step 1
 ConsumeMessageThread_4->收到消息内容 order_1 step 2
 ConsumeMessageThread_4->收到消息内容 order_1 step 3
 ConsumeMessageThread_4->收到消息内容 order_1 step 4
 ConsumeMessageThread_4->收到消息内容 order_1 step 5
 ConsumeMessageThread_5->收到消息内容 order_2 step 0
 ConsumeMessageThread_5->收到消息内容 order_2 step 1
 ConsumeMessageThread_5->收到消息内容 order_2 step 2
 ConsumeMessageThread_5->收到消息内容 order_2 step 3
 ConsumeMessageThread_5->收到消息内容 order_2 step 4
 ConsumeMessageThread_5->收到消息内容 order_2 step 5
 */