package org.hzz.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class SingleServer {
    private static Logger logger = Logger.getLogger("SingleServer");
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8080));
        logger.info("服务器启动成功");
        int count = 0;
        try{
            while(true) {
                // 等待客户端链接
                Socket socket = serverSocket.accept();
                logger.info("客户端链接成功");

                try(
                        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                        ){
                    // 读取客户端发送的数据
                    String username = inputStream.readUTF();
                    logger.info("客户端发送的数据为：" + username);

                    // 向客户端发送数据
                    outputStream.writeUTF("hello " + username);
                    outputStream.flush();
                }finally {
                    socket.close();
                }
            }
        }finally {
            serverSocket.close();
        }

    }
}
