package org.hzz.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Logger;

public class Client {
    private static Logger logger = Logger.getLogger("Client");
    public static void main(String[] args) throws IOException {
        //客户端启动必备
        Socket socket = null;
        //实例化与服务端通信的输入输出流
        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        //服务器的通信地址
        InetSocketAddress addr
                = new InetSocketAddress("127.0.0.1",8080);

        try{
            socket = new Socket();
            socket.connect(addr);//连接服务器
            logger.info("Connect Server success!!");
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
            logger.info("Ready send message.....");
            /*向服务器输出请求*/
            output.writeUTF("Q10Viking");
            output.flush();

            //接收服务器的输出
            logger.info(input.readUTF());
        }finally{
            if (socket!=null) socket.close();
            if (output!=null) output.close();
            if (input!=null) input.close();

        }


    }
}
