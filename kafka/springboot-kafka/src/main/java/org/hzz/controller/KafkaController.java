package org.hzz.controller;

import lombok.extern.slf4j.Slf4j;
import org.hzz.config.KafkaConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/kafka")
@Slf4j
public class KafkaController {

    private static final AtomicInteger count = new AtomicInteger(0);
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @GetMapping("/send")
    public String send(@RequestParam("message") String message) {
        kafkaTemplate.send(KafkaConsts.TOPIC,String.valueOf(count.getAndIncrement()) ,message);
        log.info("send message:{} - {}", count.get(),message);
        return "success: "+count.get()+message;
    }
}
