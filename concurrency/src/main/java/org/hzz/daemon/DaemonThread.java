package org.hzz.daemon;

public class DaemonThread extends Thread{

    public DaemonThread(){
        // 设置为守护线程
        this.setDaemon(true);
    }
    @Override
    public void run() {
        System.out.println("我是守护线程，每个一秒发送一次心跳，我依赖与主线程，主线程结束我就结束");
        int count = 1;
        boolean flag = false;
        while(true){
            try {
                System.out.printf("发送心跳%d次\n",count++);
                Thread.sleep(1000);
                if(flag) break;  // 方便编译器通过最后的System.out.println("守护线程结束")
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("守护线程结束");
    }
}
