package org.hzz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BioClient {
    public static void main(String[] args) throws Exception {
        //客户端启动必备
        Socket socket = null;
        //实例化与服务端通信的输入输出流
        OutputStream output = null;
        InputStream input = null;
        //服务器的通信地址
        InetSocketAddress addr
                = new InetSocketAddress("127.0.0.1",8888);

        try{
            socket = new Socket();
            socket.connect(addr);//连接服务器
            System.out.println("Connect Server success!!");
            output = socket.getOutputStream();
            input = socket.getInputStream();

            byte[] bytes = "body-key1=body-val1".getBytes(StandardCharsets.UTF_8);
            output.write(bytes);

            System.out.println("5s后关闭socket");
            Thread.sleep(5000);
            socket.close();
//            System.out.println("提前关闭socket");
//            Thread.sleep(50000);
            byte[] b = new byte[1024];
            int i = 0;
            int lineNumber = 0;
            output.write(bytes);
            while( (i = input.read(b)) != -1){
                String s = new String(b, 0,i, StandardCharsets.UTF_8);
                System.out.println(++lineNumber+": "+s);
            }
            System.out.println("服务端关闭socket,此时： i = "+i);
        }finally{
            if (socket!=null) socket.close();
            if (output!=null) output.close();
            if (input!=null) input.close();

        }
    }
}
