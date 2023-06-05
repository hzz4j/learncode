package org.hzz;

import org.hzz.entity.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;

@SpringBootApplication
public class SpringbootJmsActivemqApplication {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJmsActivemqApplication.class, args);
    }

    @Bean
    public CommandLineRunner testStr(){
        return args -> {
            jmsTemplate.convertAndSend("str.queue", "hello jms activemq");
        };
    }


    @Bean
    public CommandLineRunner testEntity() {
        return args -> {
            jmsTemplate.convertAndSend("entity.queue"
                    , new Email("q10viking", "hello jms activemq"));
        };
    }
}
