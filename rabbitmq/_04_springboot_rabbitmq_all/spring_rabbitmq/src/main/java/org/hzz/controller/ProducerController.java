package org.hzz.controller;

import io.swagger.annotations.ApiOperation;
import org.hzz.constant.ExchangeConstant;
import org.hzz.constant.QueueConstant;
import org.hzz.constant.RouteKeyConstant;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
public class ProducerController {
    @Autowired
    private RabbitTemplate rabbitTemplate;


    /** ------------------helloworld模式-----------------------------*/
    @ApiOperation(value="helloWorld发送接口",notes="直接发送到队列")
    @GetMapping("/helloWorldSend")
    public Object helloWorldSend(String msg) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        rabbitTemplate.send(QueueConstant.HELLO_WORLD_QUEUE,new Message(msg.getBytes(StandardCharsets.UTF_8),messageProperties));
        return "message send: "+msg;
    }

    /**-------------------------------------------------------------*/

    /** ------------------工作队列模式--------------------------------*/
    @ApiOperation(value="workqueue发送接口",notes="发送到所有监听该队列的消费")
    @GetMapping(value="/workqueueSend")
    public Object workqueueSend(String msg)  {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        //制造多个消息进行发送操作
        for (int i = 0; i <10 ; i++) {
            rabbitTemplate.send(QueueConstant.WORK_QUEUE,  new Message((msg+i).getBytes(StandardCharsets.UTF_8),messageProperties));
        }
        return "message sended: "+msg;
    }
    /**-------------------------------------------------------------*/



    /** ------------------发布订阅模式--------------------------------*/
    @ApiOperation(value="fanout发送接口",notes="发送到fanoutExchange。消息将往该exchange下的所有queue转发")
    @GetMapping(value="/fanoutSend")
    public Object fanoutSend(String msg)  {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        //fanout模式只往exchange里发送消息。分发到exchange下的所有queue
        rabbitTemplate.send(ExchangeConstant.FANOUT_EXCHANGE, "", new Message(msg.getBytes(StandardCharsets.UTF_8),messageProperties));
        return "message sended : "+msg;
    }
    /**-------------------------------------------------------------*/


    /** ------------------Routing模式--------------------------------*/
    @ApiOperation(value="direct发送接口",notes="发送到directExchange。exchange转发消息时，会往routingKey匹配的queue发送")
    @GetMapping(value="/directSend")
    public Object routingSend(String routingKey,String message)  {

        if(null == routingKey) {
            routingKey= RouteKeyConstant.DIRECT_ROUTE_KEY_BEI_JING;
        }
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        //fanout模式只往exchange里发送消息。分发到exchange下的所有queue
        rabbitTemplate.send(ExchangeConstant.DIRECT_EXCHANGE, routingKey, new Message(message.getBytes(StandardCharsets.UTF_8),messageProperties));
        return "message sended : routingKey >"+routingKey+";message > "+message;
    }
    /**-------------------------------------------------------------*/


    /** ------------------Topic模式--------------------------------*/
    @ApiOperation(value="topic发送接口",notes="发送到topicExchange。exchange转发消息时，会往routingKey匹配的queue发送，*代表一个单词，#代表0个或多个单词。")
    @GetMapping(value="/topicSend")
    public Object topicSend(String routingKey,String message)  {

        if(null == routingKey) {
            routingKey="changsha.kf";
        }
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
        //fanout模式只往exchange里发送消息。分发到exchange下的所有queue
        rabbitTemplate.send(ExchangeConstant.TOPIC_EXCHANGE, routingKey, new Message(message.getBytes(StandardCharsets.UTF_8),messageProperties));
        return "message sended : routingKey >"+routingKey+";message > "+message;
    }
    /**-------------------------------------------------------------*/

}
