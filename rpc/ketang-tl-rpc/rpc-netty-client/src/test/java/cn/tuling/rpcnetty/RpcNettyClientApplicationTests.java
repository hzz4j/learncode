package cn.tuling.rpcnetty;

import cn.tuling.rpcnetty.remote.SendSms;
import cn.tuling.rpcnetty.remote.vo.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RpcNettyClientApplicationTests {
    @Autowired
    private SendSms sendSms;

    @Test
    void contextLoads() throws InterruptedException {
        long start = System.currentTimeMillis();
        /*发送邮件*/
        UserInfo userInfo = new UserInfo("Mark","Mark@xiangxue.com");
        System.out.println("Send mail: "+ sendSms.sendMail(userInfo));
        System.out.println("共耗时："+(System.currentTimeMillis()-start)+"ms");
        Thread.sleep(3000);
    }

}
