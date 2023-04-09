package org.hzz.client;

import java.io.IOException;
import java.util.Scanner;

import static org.hzz.helper.Const.DEFAULT_SERVER_IP;
import static org.hzz.helper.Const.DEFAULT_PORT;

public class NioClient {
    private ClientHandler clientHandler;
    public NioClient() {
        clientHandler = new ClientHandler(DEFAULT_SERVER_IP,DEFAULT_PORT);
    }

    public void start(){
        new Thread(clientHandler,"Client").start();
    }

    public boolean sendMsg(String msg){
        return clientHandler.sendMsg(msg);
    }

    public static void main(String[] args) throws IOException {
        NioClient nioClient = new NioClient();
        nioClient.start();
        Scanner scanner = new Scanner(System.in);
        while (nioClient.sendMsg(scanner.nextLine()));
    }
}
/**
 * 四月 09, 2023 8:22:33 下午 org.hzz.client.ClientHandler doConnect
 * 信息: doConnect 连接失败 注册OP_CONNECT
 * 四月 09, 2023 8:22:33 下午 org.hzz.client.ClientHandler handleInput
 * 信息: handleInput 连接成功
 * 四月 09, 2023 8:22:33 下午 org.hzz.client.ClientHandler handleInput
 * 信息: true
 * 你好
 * 四月 09, 2023 8:22:52 下午 org.hzz.client.ClientHandler doWrite
 * 信息: 客户端发送消息成功：你好
 * 四月 09, 2023 8:22:52 下午 org.hzz.client.ClientHandler handleInput
 * 信息: 客户端收到消息：Hello,你好,Now is 2023-04-09T20:22:52.181
 */