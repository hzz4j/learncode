package org.hzz.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class RabbitUtils {
    static Logger log = Logger.getLogger(RabbitUtils.class);
    private static ConnectionFactory connectionFactory = new ConnectionFactory();
    static {
//        connectionFactory.setHost("learning.rabbitmq.org");
        connectionFactory.setHost("192.168.187.135");
        connectionFactory.setPort(5672);//5672是RabbitMQ的默认端口号
        connectionFactory.setUsername("hzz");
        connectionFactory.setPassword("root.123456");
        connectionFactory.setVirtualHost("/");
    }
    public static Connection getConnection(){
        Connection conn = null;
        try {
            BasicConfigurator.configure();
            conn = connectionFactory.newConnection();
            log.info("成功连接到： "+conn.getAddress()+":"+conn.getPort());
            return conn;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
