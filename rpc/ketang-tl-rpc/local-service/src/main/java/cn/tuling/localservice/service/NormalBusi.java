package cn.tuling.localservice.service;

import org.springframework.stereotype.Service;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 往期课程咨询芊芊老师  QQ：2130753077 VIP课程咨询 依娜老师  QQ：2133576719
 * 类说明：
 */
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
