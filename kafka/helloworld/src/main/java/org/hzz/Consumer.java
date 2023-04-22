package org.hzz;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.hzz.utils.Consts;
import org.hzz.vo.User;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;

@Slf4j
public class Consumer {
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.29.96.105:9092");
        // 反序列化器
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "java-kafka-consumer-group");
        // 自动提交offset
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        // 连接超时时间
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
        // 自动提交offset的时间间隔
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000);
        // 消费者的ID
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, "192.168.135.1");
        // 从头开始消费 --from-beginning
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = null;
        try {
            // 1. 创建消费者对象
            consumer = new KafkaConsumer<>(properties);
            // 2. 订阅主题
            consumer.subscribe(Collections.singleton(Consts.TOPIC));
            // 3. 拉取数据
            for (; ; ) {
                // 拉去数据
                ConsumerRecords<String, String> records = consumer.poll(1000);
                if(records.count()!=0 ) System.out.println("总共消息数量: "+records.count());
                // 获取分区
                Set<TopicPartition> partitions = records.partitions();
                for (TopicPartition partition : partitions) {
                    // 获取每个分区的数据,分区内的数据是有序的
                    records.records(partition).forEach(record -> {
                        System.out.println("分区：" + partition.partition() + " 偏移量：" + record.offset() + " key：" + record.key() + " value：" + record.value());
                    });
                }

            }
        } finally {
            if (consumer != null) consumer.close();
        }
    }
}
/**
 * 总共消息数量: 10
 * 分区：0 偏移量：2 key：null value：{"id":0,"username":"Q10Viking"}
 * 分区：0 偏移量：3 key：null value：{"id":1,"username":"Q10Viking"}
 * 分区：0 偏移量：4 key：null value：{"id":2,"username":"Q10Viking"}
 * 分区：0 偏移量：5 key：null value：{"id":3,"username":"Q10Viking"}
 * 分区：0 偏移量：6 key：null value：{"id":4,"username":"Q10Viking"}
 * 分区：0 偏移量：7 key：null value：{"id":5,"username":"Q10Viking"}
 * 分区：0 偏移量：8 key：null value：{"id":6,"username":"Q10Viking"}
 * 分区：0 偏移量：9 key：null value：{"id":7,"username":"Q10Viking"}
 * 分区：0 偏移量：10 key：null value：{"id":8,"username":"Q10Viking"}
 * 分区：0 偏移量：11 key：null value：{"id":9,"username":"Q10Viking"}
 */