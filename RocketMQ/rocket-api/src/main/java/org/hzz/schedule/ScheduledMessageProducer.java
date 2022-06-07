package org.hzz.schedule;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.hzz.consts.Addr;

public class ScheduledMessageProducer {
    public static void main(String[] args) throws Exception {
        // Instantiate a producer to send scheduled messages
        DefaultMQProducer producer = new DefaultMQProducer("schedule_producer_group");
        producer.setNamesrvAddr(Addr.NAME_SERVER_ADDR);
        // Launch producer
        producer.start();
        int totalMessagesToSend = 100;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("schdule_topic", ("Hello scheduled message " + i).getBytes());
            // This message will be delivered to consumer 1 min later.
            // 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            message.setDelayTimeLevel(5);
            // Send the message
            producer.send(message);
        }

        // Shutdown producer after use.
        producer.shutdown();
        System.out.println("Send finished");
    }
}
