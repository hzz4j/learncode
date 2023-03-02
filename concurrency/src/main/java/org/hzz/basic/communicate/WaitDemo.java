package org.hzz.basic.communicate;

public class WaitDemo {
    private static Object lock = new Object();
    private static volatile boolean flag = true;
    public static void main(String[] args) {

        new Thread(()->{
            synchronized (lock){
                System.out.println(Thread.currentThread().getName() + "获得锁");
                int count = 1;
                while (flag){
                    try {
                        System.out.println(Thread.currentThread().getName()+"执行"+(count++) + "次" );
                        Thread.sleep(5000);  // 不会释放锁
                        System.out.println("wait start");
                        lock.wait();  // 线程被唤醒后接着执行,并且会释放锁
                        System.out.println("被唤醒");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.printf("%s over\n",Thread.currentThread().getName());
            }
        },"t1").start();


        new Thread(()->{
            synchronized (lock){
                System.out.println(Thread.currentThread().getName() + "获得锁");
                flag = false;
                lock.notify();
                System.out.printf("%s over\n",Thread.currentThread().getName());
            }
        },"t2").start();
    }
}
/**
 * t1获得锁
 * t1执行1次  // 接下来进输入睡眠时间，在睡眠的时候线程t1并没有释放锁
 * wait start  // 进入到WAITING状态会是释放锁
 * t2获得锁
 * t2 over
 * 被唤醒      // 唤醒之后获取到锁，继续执行
 * t1 over
 */