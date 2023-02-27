package org.hzz.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.hzz.util.RabbitConstant;
import org.hzz.util.RabbitUtils;

import java.util.Scanner;

public class Weather {
    public static void main(String[] args) throws Exception {
        Connection connection = RabbitUtils.getConnection();
        String input = new Scanner(System.in).next();
        Channel channel = connection.createChannel();

        //第一个参数交换机名字   其他参数和之前的一样
        channel.basicPublish(RabbitConstant.EXCHANGE_WEATHER,"" , null , input.getBytes());

        channel.close();
        connection.close();
    }
}
