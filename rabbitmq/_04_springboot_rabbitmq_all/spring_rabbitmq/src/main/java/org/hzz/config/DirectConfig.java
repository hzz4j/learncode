package org.hzz.config;

import org.hzz.constant.ExchangeConstant;
import org.hzz.constant.QueueConstant;
import org.hzz.constant.RouteKeyConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {

    @Bean
    public Queue pubsubQueueOne(){
        return new Queue(QueueConstant.DIRECT_QUEUE_ONE);
    }

    @Bean
    public Queue pubsubQueueTwo(){
        return new Queue(QueueConstant.DIRECT_QUEUE_TWO);
    }

    @Bean
    public DirectExchange setDirectExchange() {
        return new DirectExchange(ExchangeConstant.DIRECT_EXCHANGE);
    }

    @Bean
    public Binding bindQueueOne(){
        return BindingBuilder.bind(pubsubQueueOne())
                .to(setDirectExchange())
                .with(RouteKeyConstant.DIRECT_ROUTE_KEY_CHANG_SHA);
    }

    @Bean
    public Binding bindQueueTwo(){
        return BindingBuilder.bind(pubsubQueueTwo())
                .to(setDirectExchange())
                .with(RouteKeyConstant.DIRECT_ROUTE_KEY_BEI_JING);
    }
}
