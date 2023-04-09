package org.hzz.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ThreadServer {
    private static Logger logger = Logger.getLogger("ThreadServer");
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Thread acceptThread = new Thread(new AcceptThread(serverSocket));
        acceptThread.start();
    }

    static class AcceptThread implements Runnable{
        private ServerSocket serverSocket;
        public AcceptThread(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            while(true){
                try {
                    new Thread(new ServerTask(serverSocket.accept())).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ServerTask implements Runnable{
        private Socket socket;
        public ServerTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            logger.info(Thread.currentThread().getName()+"客户端链接成功");
            try(
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    ){
                // 读取客户端发送的数据
                String username = inputStream.readUTF();
                System.out.println("客户端发送的数据为：" + username);

                // 向客户端发送数据
                outputStream.writeUTF("hello " + username);
                outputStream.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
/**
 * 四月 09, 2023 5:23:14 下午 org.hzz.server.ThreadServer$ServerTask run
 * 信息: Thread-2客户端链接成功
 * 客户端发送的数据为：Q10Viking
 * 四月 09, 2023 5:24:01 下午 org.hzz.server.ThreadServer$ServerTask run
 * 信息: Thread-3客户端链接成功
 * 客户端发送的数据为：Q10Viking
 * 四月 09, 2023 5:24:28 下午 org.hzz.server.ThreadServer$ServerTask run
 * 信息: Thread-4客户端链接成功
 * 客户端发送的数据为：Q10Viking
 */