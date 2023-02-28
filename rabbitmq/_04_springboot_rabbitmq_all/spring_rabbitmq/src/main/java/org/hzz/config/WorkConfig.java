package org.hzz.config;

import org.hzz.constant.QueueConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkConfig {

    @Bean
    public Queue workQueue1(){
        return new Queue(QueueConstant.WORK_QUEUE);
    }

}
