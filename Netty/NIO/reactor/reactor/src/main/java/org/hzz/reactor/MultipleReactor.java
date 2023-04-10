package org.hzz.reactor;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class MultipleReactor extends WorkerThreadPoolRector{

    private final Reactor[] subReactors;
    private final AtomicInteger next = new AtomicInteger(0);
    private final ExecutorService reactorPool;

    public MultipleReactor(int port, int subReactorNum) throws IOException {
        super(port);
        reactorPool = Executors.newFixedThreadPool(subReactorNum);
        subReactors = new Reactor[subReactorNum];
        for (int i = 0; i < subReactorNum; i++) {
            subReactors[i] = new SubReactor();
        }
    }

    @Override
    public void run() {
        for (Reactor subReactor : subReactors) {
            reactorPool.execute(subReactor);
        }
        // 主Reactor
        super.run();
    }


    @Override
    public Acceptor getAcceptor() {
        return new Acceptor();
    }

    public Reactor robinSubReactor(){
        int index = next.getAndIncrement() % subReactors.length;
        logger.info("subReactor index: " + index);
        return subReactors[index];
    }

    public static void main(String[] args) {
        try {
            new Thread(new MultipleReactor(8080,2)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Acceptor extends WorkerThreadPoolRector.Acceptor{
        @Override
        public Handler getHandler(SocketChannel socketChannel) throws IOException {
            // 轮询获取subReactor
            Reactor subReactor = robinSubReactor();
            // 关键地方，将socketChannel注册到subReactor上
            return new Handler(subReactor,socketChannel);
        }
    }

    class Handler extends WorkerThreadPoolRector.Handler{
        public Handler(Reactor reactor,SocketChannel socketChannel) throws IOException {
            super(reactor, socketChannel);
        }

        @Override
        void registerHandler(Reactor reactor) throws IOException {
            SubReactor subReactor = (SubReactor) reactor;
            try {
                subReactor.lock.lock();
                reactor.getSelector().wakeup(); // 唤醒selector,让selector释放持有的锁，方便注册
                super.registerHandler(reactor);
            }finally {
                subReactor.lock.unlock();
            }
        }
    }

    class SubReactor extends Reactor{
        ReentrantLock lock = new ReentrantLock();
        public SubReactor() throws IOException {
        }

        @Override
        protected int doSelect() throws IOException {
            lock.lock();    // 注册的时候会加锁,所以想要selector需要确定没有注册的时候才能select
            lock.unlock();  // 立即释放是因为selector.select()会阻塞
            return super.doSelect();
        }
    }
}
