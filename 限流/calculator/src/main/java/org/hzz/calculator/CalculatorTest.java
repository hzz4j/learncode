package org.hzz.calculator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CalculatorTest {
    public static void main(String[] args) {
        Calculator calculator = new Calculator(5, 1000); // 创建一个限流器，限制阈值为5，时间窗口间隔为1000ms

        // 启动多个线程模拟请求
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(() -> {
                // 模拟请求，每次请求计数器加1
                calculator.increment();
                if(calculator.isExceeded()) {
                    log.info("限流");
                } else {
                   log.info("通过");
                }
                try {
                    Thread.sleep(200); // 模拟请求间隔
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
        }
    }
}
/**
 * 17:33:33.272 [Thread-1] INFO org.hzz.calculator.CalculatorTest - 通过
 * 17:33:33.272 [Thread-7] INFO org.hzz.calculator.CalculatorTest - 限流
 * 17:33:33.272 [Thread-5] INFO org.hzz.calculator.CalculatorTest - 限流
 * 17:33:33.271 [Thread-8] INFO org.hzz.calculator.CalculatorTest - 限流
 * 17:33:33.271 [Thread-9] INFO org.hzz.calculator.CalculatorTest - 限流
 * 17:33:33.272 [Thread-4] INFO org.hzz.calculator.CalculatorTest - 通过
 * 17:33:33.271 [Thread-2] INFO org.hzz.calculator.CalculatorTest - 通过
 * 17:33:33.272 [Thread-3] INFO org.hzz.calculator.CalculatorTest - 通过
 * 17:33:33.272 [Thread-0] INFO org.hzz.calculator.CalculatorTest - 通过
 * 17:33:33.272 [Thread-6] INFO org.hzz.calculator.CalculatorTest - 限流
 */