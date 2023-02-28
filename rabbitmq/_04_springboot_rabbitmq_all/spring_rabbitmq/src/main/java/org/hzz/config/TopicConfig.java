package org.hzz.config;

import org.hzz.constant.ExchangeConstant;
import org.hzz.constant.QueueConstant;
import org.hzz.constant.RouteKeyConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
    //声明队列
    @Bean
    public Queue topicQ1() {
        return new Queue(QueueConstant.TOPIC_QUEUE_ONE);
    }
    @Bean
    public Queue topicQ2() {
        return new Queue(QueueConstant.TOPIC_QUEUE_TWO);
    }


    //声明exchange
    @Bean
    public TopicExchange setTopicExchange() {
        return new TopicExchange(ExchangeConstant.TOPIC_EXCHANGE);
    }

    //声明binding，需要声明一个roytingKey
    @Bean
    public Binding bindTopicHebei1() {
        return BindingBuilder.bind(topicQ1()).to(setTopicExchange()).with(RouteKeyConstant.TOPIC_ROUTE_KEY_CHANG_SHA);
    }
    @Bean
    public Binding bindTopicHebei2() {
        return BindingBuilder.bind(topicQ2()).to(setTopicExchange()).with(RouteKeyConstant.TOPIC_ROUTE_KEY_BEI_JING);
    }
}
