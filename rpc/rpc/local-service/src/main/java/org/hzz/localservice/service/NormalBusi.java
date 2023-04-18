package org.hzz.localservice.service;

import org.springframework.stereotype.Service;


@Service
public class NormalBusi {

    public void business(){
        System.out.println("订单业务操作，比如扣款等");
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
