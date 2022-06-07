package org.hzz;

import org.apache.commons.io.input.ReaderInputStream;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BioServer {
    public static void main(String[] args) throws IOException {
        //服务端启动必备
        ServerSocket serverSocket = new ServerSocket();
        //表示服务端在哪个端口上监听
        serverSocket.bind(new InetSocketAddress(8888));
        System.out.println("Start Server ....");
        int connectCount = 0;
        try {
            while(true){
                Socket socket = serverSocket.accept();

                System.out.println("accept client socket ....total =" + ( ++connectCount));
                //实例化与客户端通信的输入输出流
                try(
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        InputStream reader = socket.getInputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                ){

                    //接收客户端的输出，也就是服务器的输入
                    String line = null;
                    byte[] b = new byte[1024];
                    int i = 0;
                    int lineNumber = 0;
                    while( (i = reader.read(b)) != -1){
                        String s = new String(b, 0,i,StandardCharsets.UTF_8);
                        System.out.println(++lineNumber+": "+s);
                        // 模拟读到一个完整的请求
                        if(s.contains("body-key1=body-val12")){
                            System.out.println("----------get a complete request-----------------");
                            break;
                        }
                    }

                    System.out.println("-----------------------------------------------");
                    System.out.println("此时 i = "+i);
                    System.out.println("再次读");
                    i = reader.read(b);
                    System.out.println("此时 i = "+i);
                    System.out.println("-----------------------------------------------");

                    System.out.println("开始响应");
                    // ------------------------响应头----------------------------------
                    writer.write("HTTP/1.1 200 OK");
                    writer.newLine();
                    writer.write("Connection: keep-alive");
                    writer.newLine();
                    writer.write("Server: hzz-server");
                    writer.newLine();
                    writer.write("Content-Type: text/html;charset=utf-8");
                    writer.newLine();
                    writer.write("xxx: hello world");
                    writer.newLine();
                    writer.newLine();
                    writer.write("test msg1");
                    writer.flush();
                    Thread.sleep(2000);
                    writer.write("test msg2");
                    writer.flush();
                    Thread.sleep(12000);
                    writer.write("test msg3");
                    System.out.println("结束");
                }catch(Exception e){
                    e.printStackTrace();
                }finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            serverSocket.close();
        }

    }
}
