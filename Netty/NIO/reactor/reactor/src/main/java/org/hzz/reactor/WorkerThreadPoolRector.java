package org.hzz.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WorkerThread Pools
 */
public class WorkerThreadPoolRector extends BasicReactor{
    protected static ExecutorService threadPools = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 4
    );
    public WorkerThreadPoolRector(int port) throws IOException {
        super(port);
    }

    @Override
    public Acceptor getAcceptor() {
        return new Acceptor();
    }

    public static void main(String[] args) throws IOException {
        new Thread(new WorkerThreadPoolRector(8080)).start();
    }

    class Acceptor extends BasicReactor.Acceptor{
        @Override
        public Handler getHandler(SocketChannel socketChannel) throws IOException {
            return new Handler(WorkerThreadPoolRector.this,socketChannel);
        }
    }

    class Handler extends BasicReactor.Handler{
        public Handler(Reactor reactor,SocketChannel socketChannel) throws IOException {
            super(reactor,socketChannel);
        }

        synchronized void processAndHandOff() {
            super.process();
            setState(SENDING);
            selectionKey.interestOps(SelectionKey.OP_WRITE);
            // 唤醒selector,让其处理发送事件,不然的话，selector一直处于阻塞状态。上面的是select已经阻塞了
            // 而线程池处理到这里的时候,才刚刚注册OP_WRITE,selector还是阻塞的，所以需要唤醒
            reactor.getSelector().wakeup();
        }

        @Override
        void onInputComplete() {
            setState(PROCESSING);
            // 读取完毕，交给线程池处理
            threadPools.execute(new Processer());
        }

        class Processer implements Runnable{
            @Override
            public void run() {processAndHandOff();}
        }
    }
}
