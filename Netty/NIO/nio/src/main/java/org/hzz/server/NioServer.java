package org.hzz.server;

import static org.hzz.helper.Const.DEFAULT_PORT;
public class NioServer {
    private ServerHandler serverHandler;

    public NioServer() {
        serverHandler = new ServerHandler(DEFAULT_PORT);
    }

    public void start(){
        new Thread(serverHandler,"Server").start();
    }

    public static void main(String[] args) {
        new NioServer().start();
    }
}
/**
 * 四月 09, 2023 8:22:22 下午 org.hzz.server.ServerHandler <init>
 * 信息: 服务器已启动，端口号：8080
 * 四月 09, 2023 8:22:33 下午 org.hzz.server.ServerHandler handleInput
 * 信息: 接受到新的连接：/127.0.0.1:14804
 * 四月 09, 2023 8:22:52 下午 org.hzz.server.ServerHandler handleInput
 * 信息: 接受到消息：你好
 */