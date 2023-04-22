package org.hzz;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.hzz.vo.User;
import org.hzz.utils.Consts;

import java.util.Properties;

@Slf4j
public class Producer {
    public static void main(String[] args) {

        Properties properties = new Properties();
        // 服务器用逗号分割
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"172.29.96.105:9092");

        // 生产者的ID
        properties.put(ProducerConfig.CLIENT_ID_CONFIG,"hello.world.producer");

        // 序列化器
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // 超时
        properties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 100000);

        KafkaProducer<String, String> producer = null;

        try {
            // 1. 创建生产者对象
            producer = new KafkaProducer<>(properties);
            // 2. 创建消息对象
            for (int i = 0;i<10;i++) {
                User user = new User(i, "Q10Viking");
                // args: topic ,value
                ProducerRecord<String, String> record = new ProducerRecord<>(Consts.TOPIC,
                        JSON.toJSONString(user));

                // 3. 发送消息
                producer.send(record);
                log.info("消息发送成功");
            }
        }finally {
            if (producer != null) producer.close();
        }
    }
}