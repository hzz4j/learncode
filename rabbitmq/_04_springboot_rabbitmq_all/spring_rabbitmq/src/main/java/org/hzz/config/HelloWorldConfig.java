package org.hzz.config;

import org.hzz.constant.QueueConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfig {
    @Bean
    public Queue setQueue(){
        return new Queue(QueueConstant.HELLO_WORLD_QUEUE);
    }
}
