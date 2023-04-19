package org.hzz.rpc.controller;


import org.hzz.rpc.client.service.NormalBusi;
import org.hzz.rpc.remote.SendSms;
import org.hzz.rpc.remote.StockService;
import org.hzz.rpc.remote.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private SendSms sendSms;

    @Autowired
    private StockService stockService;

    @Autowired
    private NormalBusi normalBusi;

    @GetMapping("/test")
    public Object test(){
        long start = System.currentTimeMillis();
        normalBusi.business();

        /*发送邮件*/
        UserInfo userInfo = new UserInfo("Q10Viking","Q10Viking@outlook.com");
        System.out.println("Send mail: "+ sendSms.sendMail(userInfo));
        System.out.println("共耗时："+(System.currentTimeMillis()-start)+"ms");


        /*扣减库存*/
        stockService.addStock("A001",1000);
        stockService.deduceStock("B002",50);
        return "success";
    }
}
