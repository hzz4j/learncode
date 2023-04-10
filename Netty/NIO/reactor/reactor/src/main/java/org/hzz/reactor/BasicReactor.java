package org.hzz.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * Basic Reactor Design
 */
public class BasicReactor extends Reactor{
    final ServerSocketChannel serverSocketChannel;
    private volatile boolean started = false;
    public BasicReactor(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(getSelector(),
                SelectionKey.OP_ACCEPT,
                getAcceptor());
        started = true;
        logger.info("Server started at port: "+port);
    }

    public Acceptor getAcceptor(){
        return new Acceptor();
    }

    public static void main(String[] args) throws IOException{
        new BasicReactor(8080).run();
    }


    class Acceptor implements Runnable{
        @Override
        public void run() {
            try{
                SocketChannel socketChannel = serverSocketChannel.accept();
                logger.info("客户端连接"+socketChannel.getRemoteAddress());
                getHandler(socketChannel);
            }catch (IOException e){}

        }

        Handler getHandler(SocketChannel socketChannel) throws IOException {
            return new Handler(BasicReactor.this,socketChannel);
        }
    }


    class Handler implements Runnable{
        protected final static int MAXIN = 65535;
        protected final static int MAXOUT = 65535;
        static final int READING = 0, SENDING = 1, PROCESSING = 2;
        protected final SocketChannel socketChannel;
        protected  SelectionKey selectionKey;
        ByteBuffer input = ByteBuffer.allocate(MAXIN);
        ByteBuffer output = ByteBuffer.allocate(MAXOUT);
        volatile int state = READING;
        Reactor reactor;
        public Handler(Reactor reactor,SocketChannel socketChannel) throws IOException{
            this.socketChannel = socketChannel;
            this.socketChannel.configureBlocking(false);
            this.reactor = reactor;
            registerHandler(reactor);
        }


        void registerHandler(Reactor reactor) throws IOException {
            selectionKey = this.socketChannel.register(reactor.getSelector(), 0);
            selectionKey.interestOps(SelectionKey.OP_READ);
            selectionKey.attach(this);
            reactor.getSelector().wakeup();
        }

        @Override
        public void run() {
            try{
                if (state == READING) read();
                else if(state == SENDING) send();
            }catch (IOException e){}
        }

        void read() throws IOException{
            // ... read data ...
            socketChannel.read(input);
            if(isInputComplete()){
                onInputComplete();
            }
        }

        void onInputComplete() throws IOException {
            // ... do something ...
            process();
            setState(SENDING);
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }

        void send() throws IOException{
            // ... send data ...
            output.put("hello client".getBytes(StandardCharsets.UTF_8));
            output.flip();
            socketChannel.write(output);
            setState(READING);
            selectionKey.interestOps(SelectionKey.OP_READ);
            if(isOutputComplete()){
                logger.info(Thread.currentThread().getName()+"关闭"+socketChannel.getRemoteAddress());
                output.clear();
                selectionKey.cancel();
                socketChannel.close();
            }
        }

        boolean isInputComplete(){
            return true;
        }
        boolean isOutputComplete(){
            return true;
        }


        void process() {
            // ... process data ...
            if(input.position() > 0){
                input.flip();
                byte[] bytes = new byte[input.limit()];
                input.get(bytes);
                logger.info(Thread.currentThread().getName()+": read data is "+new String(bytes, StandardCharsets.UTF_8));
                input.clear();
            }
        }

        void setState(int state){
            this.state = state;
        }
    }
}
