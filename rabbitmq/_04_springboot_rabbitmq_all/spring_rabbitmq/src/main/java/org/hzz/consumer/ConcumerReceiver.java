package org.hzz.consumer;

import org.hzz.constant.QueueConstant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class ConcumerReceiver {

    /** ------------------helloworld模式-----------------------------*/
    @RabbitListener(queues = QueueConstant.HELLO_WORLD_QUEUE)
    public void helloWorldReceiver(String msg){
        System.out.println("helloWorld模式 receiver msg: "+msg);
    }

    /**-------------------------------------------------------------*/

    /** ------------------工作队列模式--------------------------------*/
    @RabbitListener(queues = {QueueConstant.WORK_QUEUE})
    public void workReceiverOne(String msg){
        System.out.println("工作模式one receiver msg: "+msg);
    }

    @RabbitListener(queues = {QueueConstant.WORK_QUEUE})
    public void workReceiverTwo(String msg){
        System.out.println("工作模式Two receiver msg: "+msg);
    }
    /**-------------------------------------------------------------*/

    /** ----------------------路由模式--------------------------------*/
    @RabbitListener(queues=QueueConstant.DIRECT_QUEUE_ONE)
    public void routingReceiveq1(String message) {

        System.out.println("Routing路由模式routingReceiveq11111 received message : " +message);
    }

    @RabbitListener(queues=QueueConstant.DIRECT_QUEUE_TWO)
    public void routingReceiveq2(String message) {

        System.out.println("Routing路由模式routingReceiveq22222 received message : " +message);
    }

    /**-------------------------------------------------------------*/

    /** ------------------发布订阅模式--------------------------------*/
    @RabbitListener(queues=QueueConstant.PUBSUB_QUEUE_ONE)
    public void fanoutReceiveq1(String message) {
        System.out.println("发布订阅模式1received message : " +message);
    }
    @RabbitListener(queues=QueueConstant.PUBSUB_QUEUE_TWO)
    public void fanoutReceiveq2(String message) {

        System.out.println("发布订阅模式2 received message : " +message);
    }
    /**-------------------------------------------------------------*/

    /** ------------------Topic模式--------------------------------*/
    @RabbitListener(queues=QueueConstant.TOPIC_QUEUE_ONE)
    public void topicReceiveq1(String message) {
        System.out.println("Topic模式 topic_sb_mq_q1 received message : " +message);
    }

    @RabbitListener(queues=QueueConstant.TOPIC_QUEUE_TWO)
    public void topicReceiveq2(String message) {
        System.out.println("Topic模式 topic_sb_mq_q2 received  message : " +message);
    }
    /**-------------------------------------------------------------*/

}
