package org.hzz.simple;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.hzz.consts.Addr;

import java.nio.charset.StandardCharsets;

@Slf4j
public class Producer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("basic_sync_producer_group");
        producer.setNamesrvAddr(Addr.NAME_SERVER_ADDR);
        producer.start();
        for (int i=0;i<10;i++){

            Message message = new Message("basic_sync_topic",
                    "hzz_tag",
                    "OrderID188",
                    ("Hello World " + i).getBytes(StandardCharsets.UTF_8));

            SendResult result = producer.send(message);
            System.out.println((i+1)+" send "+result);
        }
        System.out.println("Send Over.");
        Thread.sleep(1000000);
        producer.shutdown();
    }
}
