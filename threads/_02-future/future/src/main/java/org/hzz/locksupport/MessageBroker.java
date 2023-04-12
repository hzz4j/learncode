package org.hzz.locksupport;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.LockSupport;

public class MessageBroker {
    private Queue<String> queue = new LinkedList<>();
    private Thread consumerThread = new Thread(()->{
        consumeMessage();
    });

    void produceMessage(String message){
        synchronized (queue){
            System.out.println("生产获得锁");
            queue.add(message);
            LockSupport.unpark(consumerThread);
        }
        System.out.println("生产消释放锁");
    }

    void consumeMessage(){
        while(true){
            synchronized (queue){
                System.out.println("消费者获得锁");
                while(queue.isEmpty()){
                    LockSupport.park();  // 消费者线程阻塞,但是不会释放锁
                }
                String message = queue.poll();
                System.out.println("消费消息："+message);
            }
            System.out.println("消费者释放锁");
        }
    }

    void start(){
        consumerThread.start();
    }
    public static void main(String[] args) throws InterruptedException {
        MessageBroker messageBroker = new MessageBroker();
        messageBroker.start();
        Thread.sleep(1000);
        // Produce messages
        messageBroker.produceMessage("Message 1");

        messageBroker.produceMessage("Message 2");
        messageBroker.produceMessage("Message 3");
    }
}
