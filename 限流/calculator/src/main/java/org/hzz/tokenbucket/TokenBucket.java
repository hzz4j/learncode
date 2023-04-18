package org.hzz.tokenbucket;

import java.util.concurrent.atomic.AtomicLong;

public class TokenBucket {
    private final long capacity; // 令牌桶容量
    private final double rate; // 生成令牌的速率
    private AtomicLong tokens; // 当前令牌数量
    private long lastRefillTime; // 上次填充令牌的时间

    public TokenBucket(long capacity, double rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.tokens = new AtomicLong(capacity);
        this.lastRefillTime = System.currentTimeMillis();
    }

    // 获取令牌，返回true表示成功获取到令牌，返回false表示未获取到令牌
    public synchronized boolean getToken() {
        refillTokens(); // 填充令牌
        if (tokens.get() > 0) {
            tokens.decrementAndGet(); // 取走一个令牌
            return true;
        } else {
            return false;
        }
    }

    // 检查是否有足够的令牌可用，返回true表示有足够的令牌，返回false表示令牌不足
    public synchronized boolean checkToken() {
        refillTokens(); // 填充令牌
        return tokens.get() > 0;
    }

    // 填充令牌
    private void refillTokens() {
        long currentTime = System.currentTimeMillis();
        double elapsedTime = (currentTime - lastRefillTime) / 1000.0; // 计算经过的时间（秒）
        long tokensToAdd = (long) (elapsedTime * rate); // 计算需要添加的令牌数量

        if (tokensToAdd > 0) {
            // 使用CAS原子操作更新令牌数量
            while (true) {
                long currentTokens = tokens.get();
                long newTokens = Math.min(currentTokens + tokensToAdd, capacity); // 不超过令牌桶容量
                if (tokens.compareAndSet(currentTokens, newTokens)) {
                    lastRefillTime = currentTime; // 更新上次填充令牌的时间
                    break;
                }
            }
        }
    }
}
