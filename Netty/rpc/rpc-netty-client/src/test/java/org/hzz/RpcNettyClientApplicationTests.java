package org.hzz;

import org.hzz.remote.SendSms;
import org.hzz.remote.vo.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RpcNettyClientApplicationTests {
    @Autowired
    private SendSms sendSms;

    @Test
    public void context() throws InterruptedException {
        long start = System.currentTimeMillis();
        /*发送邮件*/
        UserInfo userInfo = new UserInfo("hzz","1193094618@.com");
        System.out.println("Send mail: "+ sendSms.sendMail(userInfo));
        System.out.println("共耗时："+(System.currentTimeMillis()-start)+"ms");
        Thread.sleep(3000);
    }

}
