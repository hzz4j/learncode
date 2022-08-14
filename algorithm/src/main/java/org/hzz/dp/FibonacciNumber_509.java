package org.hzz.dp;

public class FibonacciNumber_509 {

    /**
     * 动态规划
     */
    public int fib1(int n) {
        if(n <= 1) return n;
        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
    }

    /**
     * 循环
     */
    public int fib2(int n) {
        if(n <= 1) return n;
        int pre = 1,prePre=0,current=0;
        for (int i = 2; i <= n; i++) {
            current = pre + prePre;
            prePre = pre;
            pre = current;
        }
        return current;
    }

    /**
     * 递归
     */
    public int fib(int n) {
        if(n <= 1) return n;
        return fib(n-1) + fib(n-2);
    }
}
