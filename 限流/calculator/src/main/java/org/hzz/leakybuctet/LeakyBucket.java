package org.hzz.leakybuctet;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class LeakyBucket {
    private int capacity; // 漏桶的容量
    private int rate; // 漏桶的流出速率
    private int water; // 当前漏桶中的水量
    private long lastTime; // 上次漏水的时间戳

    public LeakyBucket(int capacity, int rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.water = 0;
        this.lastTime = System.currentTimeMillis();
    }

    // 请求漏桶中的水，返回是否通过漏桶
    public synchronized boolean request() {
        long now = System.currentTimeMillis();
        // 计算距离上次漏水的时间
        long timeElapsed = now - lastTime;
        // 计算漏出的水量
        int waterOut = (int) (timeElapsed * rate / 1000);
        // 更新漏桶中的水量和上次漏水的时间
        water = Math.max(0, water - waterOut);
        lastTime = now;

        // 判断漏桶中是否有足够的容量放入新的请求
        if (water < capacity) {
            water++;
            return true; // 漏桶通过，请求被处理
        } else {
            return false; // 漏桶已满，请求被丢弃
        }
    }

    public static void main(String[] args) {
        LeakyBucket bucket = new LeakyBucket(10, 2); // 漏桶容量为10，流出速率为2个请求/秒
        for (int i = 0; i < 20; i++) {
            boolean result = bucket.request(); // 模拟20个请求
            System.out.println("请求" + (i + 1) + "：" + (result ? "通过" : "丢弃"));
            try {
                TimeUnit.MILLISECONDS.sleep(500); // 模拟每隔500毫秒发起一个请求
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

