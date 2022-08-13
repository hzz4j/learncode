package org.hzz.workqueue;

import com.google.gson.Gson;
import com.rabbitmq.client.*;
import org.hzz.util.RabbitConstant;
import org.hzz.util.RabbitUtils;

import java.io.IOException;

public class SMSSender2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitUtils.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(RabbitConstant.QUEUE_SMS, false, false, false, null);

        //如果不写basicQos（1），则自动MQ会将所有请求平均发送给所有消费者
        //basicQos,MQ不再对消费者一次发送多个请求，而是消费者处理完一个消息后（确认后），在从队列中获取一个新的
        channel.basicQos(1);//处理完一个取一个

        channel.basicConsume(RabbitConstant.QUEUE_SMS , false , new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String jsonSMS = new String(body);
                SMS sms = new Gson().fromJson(jsonSMS, SMS.class);
                System.out.println("SMSSender2-短信发送成功:" + sms);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                channel.basicAck(envelope.getDeliveryTag() , false);
            }
        });
    }
}
/**
 * 0 [main] INFO org.hzz.util.RabbitUtils  - 成功连接到： /192.168.187.135:5672
 * SMSSender2-短信发送成功:SMS{name='乘客2', mobile='139000002', content='您的车票已预订成功'}
 * SMSSender2-短信发送成功:SMS{name='乘客10', mobile='1390000010', content='您的车票已预订成功'}
 */