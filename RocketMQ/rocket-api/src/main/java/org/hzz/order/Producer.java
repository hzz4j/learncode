package org.hzz.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.hzz.consts.Addr;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Producer {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("order_group");
        producer.setNamesrvAddr(Addr.NAME_SERVER_ADDR);
        producer.start();

        for (int i = 0; i < 5; i++) {
            int orderId = i;

            for(int j = 0 ; j <= 5 ; j ++){
                Message msg =
                        new Message("order_topic", "order_"+orderId, "KEY" + orderId,
                                ("order_"+orderId+" step " + j).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                    /**
                     * 保证相同得消息发送到同一个队列，利用队列先进先出得特性
                     */
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        Integer id = (Integer) arg;
                        int index = id % mqs.size();
                        System.out.println(mqs);
                        return mqs.get(index);
                    }
                }, orderId);

                System.out.printf("%s%n", sendResult);
            }
        }
        Thread.sleep(500_000);
        producer.shutdown();
    }
}
/** List<MessageQueue>
 [
     [topic=order_topic, brokerName=broker-a, queueId=0],
     [topic=order_topic, brokerName=broker-a, queueId=1],
     [topic=order_topic, brokerName=broker-a, queueId=2],
     [topic=order_topic, brokerName=broker-a, queueId=3],
     [topic=order_topic, brokerName=broker-b, queueId=0],
     [topic=order_topic, brokerName=broker-b, queueId=1],
     [topic=order_topic, brokerName=broker-b, queueId=2],
     [topic=order_topic, brokerName=broker-b, queueId=3]
 ]
 */