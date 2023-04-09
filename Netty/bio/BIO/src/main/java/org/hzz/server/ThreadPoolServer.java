package org.hzz.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ThreadPoolServer {
    private static Logger logger = Logger.getLogger("ThreadPoolServer");

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8080);
        Thread acceptThread = new Thread(new AcceptThread(serverSocket));
        acceptThread.start();
    }

    static class AcceptThread implements Runnable{
        private static ExecutorService executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors() * 2
        );

        private ServerSocket serverSocket;
        public AcceptThread(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }
        @Override
        public void run() {
            while(true){
                try {
                    // 线程池提交任务
                    executorService.execute(new ServerTask(serverSocket.accept()));
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
 * 四月 09, 2023 5:33:26 下午 org.hzz.server.ThreadPoolServer$ServerTask run
 * 信息: pool-1-thread-1客户端链接成功
 * 客户端发送的数据为：Q10Viking
 * 四月 09, 2023 5:34:02 下午 org.hzz.server.ThreadPoolServer$ServerTask run
 * 信息: pool-1-thread-2客户端链接成功
 * 客户端发送的数据为：Q10Viking
 */