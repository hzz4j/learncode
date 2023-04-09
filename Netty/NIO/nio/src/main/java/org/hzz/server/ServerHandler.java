package org.hzz.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

public class ServerHandler implements Runnable {
    private static Logger logger = Logger.getLogger(ServerHandler.class.getName());
    private volatile boolean started;
    private final int port;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    public ServerHandler(int port) {
        this.port = port;
        try {
            /*创建选择器的实例*/
            selector = Selector.open();
            /*创建ServerSocketChannel的实例*/
            serverSocketChannel = ServerSocketChannel.open();
            /*设置通道为非阻塞模式*/
            serverSocketChannel.configureBlocking(false);
            /*绑定端口*/
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            /*注册事件，表示关心客户端连接*/
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            logger.info("服务器已启动，端口号：" + port);
            started = true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (started) {
            try {
                /*阻塞等待就绪的Channel*/
                selector.select();
                /*获取事件的集合*/
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    /*处理事件*/
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            /*取消特定的注册关系*/
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            /*处理新接入的请求消息*/
            if (key.isAcceptable()) {
                /*获取关心当前事件的Channel*/
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                /*接受连接*/
                SocketChannel sc = ssc.accept();
                logger.info("接受到新的连接：" + sc.getRemoteAddress());
                /*设置为非阻塞模式*/
                sc.configureBlocking(false);
                /*注册读事件*/
                sc.register(selector, SelectionKey.OP_READ);
            }
            /*读消息*/
            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                /*创建ByteBuffer，开辟一个缓冲区*/
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(buffer);
                if (readBytes > 0) {
                    /*将缓冲区当前的limit设置为position=0，用于后续对缓冲区的读取操作*/
                    buffer.flip();
                    /*根据缓冲区可读字节数创建字节数组*/
                    byte[] bytes = new byte[buffer.remaining()];
                    /*将缓冲区可读字节数组复制到新建的数组中*/
                    buffer.get(bytes);
                    String msg = new String(bytes, "UTF-8");
                    logger.info("接受到消息：" + msg);
                    /*回复消息*/
                    String response = "Hello," + msg + ",Now is " + LocalDateTime.now();
                    doWrite(sc, response);
                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException {
        /*将消息编码为字节数组*/
        byte[] bytes = response.getBytes();
        /*根据数组容量创建ByteBuffer*/
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        /*将字节数组复制到缓冲区*/
        writeBuffer.put(bytes);
        /*flip操作*/
        writeBuffer.flip();
        /*发送缓冲区的字节数组*/
        channel.write(writeBuffer);
    }

    public void stop() {
        started = false;
    }
}