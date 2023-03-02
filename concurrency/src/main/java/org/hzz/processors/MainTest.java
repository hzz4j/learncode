package org.hzz.processors;

public class MainTest {
    public static void main(String[] args) {
        // 获取机器上的逻辑核
        System.out.println(Runtime
                .getRuntime()
                .availableProcessors()); // 12
    }
}
