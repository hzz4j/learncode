package org.hzz.template;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Client {
    private static Logger logger = Logger.getLogger(Client.class.getName());
    private ClientHandler clientHandler;
    public Client() {
        clientHandler = new ClientHandler("127.0.0.1",8080);
    }

    public void start(){
        new Thread(clientHandler,"Client").start();
    }

    public void stop() throws IOException {
        clientHandler.stop();
    }

    public boolean sendMsg(String msg){
        return clientHandler.sendMsg(msg);
    }

    public static void main(String[] args) throws IOException {
        Client nioClient = new Client();
        nioClient.start();
        Scanner scanner = new Scanner(System.in);
        while (nioClient.sendMsg(scanner.nextLine()));
        logger.info("客户端退出");
        nioClient.stop();
    }

    class ClientHandler implements Runnable{
        private final String ip;
        private final int port;
        private Selector selector;
        private SocketChannel socketChannel;
        private volatile boolean started;

        public ClientHandler(String ip, int port) {
            this.ip = ip;
            this.port = port;
            try {
                selector = Selector.open();
                socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(false);
                started = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void stop() throws IOException {
            started = false;
            selector.wakeup();
            socketChannel.close();
        }

        @Override
        public void run() {
            doConnect();
            while (started){
                try {
                    logger.info("阻塞在Selector.select()方法上");
                    selector.select();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while(iterator.hasNext()){
                        SelectionKey key = iterator.next();
                    /*我们必须首先将处理过的 SelectionKey 从选定的键集合中删除。
                    如果我们没有删除处理过的键，那么它仍然会在主集合中以一个激活
                    的键出现，这会导致我们尝试再次处理它。*/
                        iterator.remove();
                        handleInput(key);
                    }
                } catch (IOException e) {
                    logger.info("失去连接");
                    throw new RuntimeException(e);
                }
            }
        }

        private void handleInput(SelectionKey key) throws IOException {
            if (key.isValid()){
                SocketChannel sc = (SocketChannel) key.channel();
                if (key.isConnectable()){
                    if (sc.finishConnect()){
                        //TODO
                        logger.info("handleInput 连接成功");
                        logger.info(String.valueOf(sc == socketChannel));
                        sc.register(selector, SelectionKey.OP_READ);
                    }else {
                        System.exit(1);
                    }
                } else if (key.isReadable()) {
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int readBytes = sc.read(readBuffer);
                    if (readBytes > 0) {
                        readBuffer.flip();
                        byte[] bytes = new byte[readBuffer.remaining()];
                        readBuffer.get(bytes);
                        String body = new String(bytes, "UTF-8");
                        logger.info("客户端收到消息：" + body);
                    } else if (readBytes < 0) {
                        key.cancel();
                        sc.close();
                    } else {
                        //读到0字节，忽略
                    }
                }
            }
        }

        private void doConnect() {
            try {
                if (socketChannel.connect(new InetSocketAddress(ip, port))) {
                    logger.info("doConnect 连接成功");
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else {
                    logger.info("doConnect 连接失败 注册OP_CONNECT");
                    socketChannel.register(selector, SelectionKey.OP_CONNECT);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void doWrite(SocketChannel channel, String request) throws IOException {
            byte[] bytes = request.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
            logger.info("客户端发送消息成功：" + request);
        }

        //写数据对外暴露的API
        public boolean sendMsg(String msg)  {
            try {
                doWrite(socketChannel,msg);
                return true;
            }catch (IOException e) {
                logger.info("客户端发送消息失败：" + msg);
                e.printStackTrace();
                return false;
            }
        }
    }
}
