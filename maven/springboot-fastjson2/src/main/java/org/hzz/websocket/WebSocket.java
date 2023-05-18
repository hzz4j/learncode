package org.hzz.websocket;

import lombok.ToString;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{name}")
@ToString
public class WebSocket {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(WebSocket.class);
    /**
     * 与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    private String name;

    public WebSocket(){
        log.info("WebSocket的构造器被调用");
    }

    /**
     * 用于存所有的连接服务的客户端，这个对象存储是安全的
     * 注意这里的kv,设计的很巧妙，v刚好是本类 WebSocket (用来存放每个客户端对应的MyWebSocket对象)
     */
    private static ConcurrentHashMap<String, WebSocket> webSocketSet = new ConcurrentHashMap<>();


    /**
     * 连接建立成功调用的方法
     * session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "name") String name) {
        log.info("----------------------------------");
        this.session = session;
        this.name = name;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(name, this);  // 客户端每打开一个客户端连接tomcat就会通过反射创建一个对象
        log.info("[WebSocket] 连接成功，当前连接人数为：={}", webSocketSet.size());
        log.info("----------------------------------");
        log.info("");
        log.info(webSocketSet.toString());

        GroupSending(name + " 来了");
    }

    /**
     * 群发
     *
     * @param message
     */
    public void GroupSending(String message) {
        for (String name : webSocketSet.keySet()) {
            try {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void OnMessage(String messageStr) {
        log.info("[WebSocket] 收到消息：{}", messageStr);
        //判断是否需要指定发送，具体规则自定义
        //message_str的格式 TOUSER:user2;message:aaaaaaaaaaaaaaaaaa;
        if (messageStr.indexOf("TOUSER") == 0) {
            //取出 name和message的值
            String[] split = messageStr.split(";");
            String[] split1 = split[0].split(":");
            String[] split2 = split[1].split(":");
            String name = split1[1];
            String message = split2[1];
            //指定发送
            AppointSending(name, message);
        } else {
            //群发
            GroupSending(messageStr);
        }
    }

    /**
     * 指定发送
     *
     * @param name
     * @param message
     */
    public void AppointSending(String name, String message) {
        try {
            webSocketSet.get(name).session.getBasicRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
