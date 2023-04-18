package cn.tuling.rpc.client;

import cn.tuling.rpc.client.service.NormalBusi;
import cn.tuling.rpc.remote.SendSms;
import cn.tuling.rpc.remote.vo.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RpcClientApplicationTests {

    @Autowired
    private NormalBusi normalBusi;
//    @Autowired
//    private StockService stockService;
    @Autowired
    private SendSms sendSms;

    @Test
    void rpcTest() {
        long start = System.currentTimeMillis();
        normalBusi.business();

        /*发送邮件*/
        UserInfo userInfo = new UserInfo("Mark","Mark@xiangxue.com");
        System.out.println("Send mail: "+ sendSms.sendMail(userInfo));
        System.out.println("共耗时："+(System.currentTimeMillis()-start)+"ms");


        /*扣减库存*/
//        stockService.addStock("A001",1000);
//        stockService.deduceStock("B002",50);
    }

}
