package org.hzz.kafkalistener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hzz.config.KafkaConsts;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    /**
     * @KafkaListener(groupId = "testGroup", topicPartitions = {
     *             @TopicPartition(topic = "topic1", partitions = {"0", "1"}),
     *             @TopicPartition(topic = "topic2", partitions = "0",
     *                     partitionOffsets = @PartitionOffset(partition = "1", initialOffset = "100"))
     *     },concurrency = "6")
     *  //concurrency就是同组下的消费者个数，就是并发消费数，必须小于等于分区总数
     * @param record
     */
    @KafkaListener(topics = KafkaConsts.TOPIC, groupId = "q10viking-group",concurrency = "3")
    public void listenJavaCLusterTopic(ConsumerRecord<String,String> record, Acknowledgment ack) {
        log.info("listenJavaCLusterTopic receive message: partition={} offset={} key={} - value={}",
                record.partition(),record.offset(), record.key(),record.value());
        ack.acknowledge();
    }
    /**
     * partition=2 offset=5 key=3 - value=learning kafka
     * partition=1 offset=0 key=4 - value=learning kafka
     * partition=0 offset=5 key=5 - value=learning kafka
     * partition=1 offset=1 key=6 - value=learning kafka
     * partition=0 offset=6 key=7 - value=learning kafka
     */
}
