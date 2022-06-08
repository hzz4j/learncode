package org.hzz.basic;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.hzz.consts.Topics;
import org.springframework.stereotype.Component;

/**
 * 注意下@RocketMQMessageListener这个注解的其他属性
 **/
@Component
@RocketMQMessageListener(consumerGroup = "springboot_consumer_group",
        topic = Topics.TOPIC,consumeMode = ConsumeMode.CONCURRENTLY)

public class SpringConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("Received message : "+ message);
    }
}
