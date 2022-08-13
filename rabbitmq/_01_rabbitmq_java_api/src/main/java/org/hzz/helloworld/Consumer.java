package org.hzz.helloworld;

import com.rabbitmq.client.*;
import org.hzz.util.RabbitConstant;
import org.hzz.util.RabbitUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取TCP长连接
        Connection conn = RabbitUtils.getConnection();
        //创建通信“通道”，相当于TCP中的虚拟连接
        Channel channel = conn.createChannel();

        //创建队列,声明并创建一个队列，如果队列已存在，则使用这个队列
        //第一个参数：队列名称ID
        //第二个参数：是否持久化，false对应不持久化数据，MQ停掉数据就会丢失
        //第三个参数：是否队列私有化，false则代表所有消费者都可以访问，true代表只有第一次拥有它的消费者才能一直使用，其他消费者不让访问
        //第四个：是否自动删除,false代表连接停掉后不自动删除掉这个队列
        //其他额外的参数, null
        channel.queueDeclare(RabbitConstant.QUEUE_HELLOWORLD,false, false, false, null);

        //从MQ服务器中获取数据

        //创建一个消息消费者
        //第一个参数：队列名
        //第二个参数代表是否自动确认收到消息，false代表手动编程来确认消息，这是MQ的推荐做法
        //第三个参数要传入DefaultConsumer的实现类
        channel.basicConsume(RabbitConstant.QUEUE_HELLOWORLD, false, new Reciver(channel));
    }
}

class  Reciver extends DefaultConsumer {
    private Channel channel;
    //重写构造函数,Channel通道对象需要从外层传入，在handleDelivery中要用到
    public Reciver(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

        String message = new String(body);
        System.out.println("消费者接收到的消息："+message);

        System.out.println("消息的TagId："+envelope.getDeliveryTag());
        //false只确认签收当前的消息，设置为true的时候则代表签收该消费者所有未签收的消息
        channel.basicAck(envelope.getDeliveryTag(), false);
    }
}
/**
 * 0 [main] INFO org.hzz.util.RabbitUtils  - 成功连接到： /192.168.187.135:5672
 * 消费者接收到的消息：静默: hello World
 * 消息的TagId：1
 */