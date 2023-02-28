package org.hzz.chain_of_responsibility.simple;

import org.hzz.chain_of_responsibility.simple.handler.Handler;
import org.hzz.chain_of_responsibility.simple.handler.LoggingHandler;
import org.hzz.chain_of_responsibility.simple.handler.RequestFrequentHandler;

public class MainTest {
    public static void main(String[] args) {
        Request request = new Request.Builder()
                .frequentOk(false)
                .loggedOn(false)
                .build();

        Handler handler = new RequestFrequentHandler(new LoggingHandler(null));

        if(handler.process(request)){
            System.out.println("业务正常处理");
        }else{
            System.out.println("访问异常");
        }
    }
}
/**
 * 访问频率控制.
 * 访问异常
 */