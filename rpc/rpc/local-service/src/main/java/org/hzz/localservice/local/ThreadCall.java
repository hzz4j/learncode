package org.hzz.localservice.local;


import org.hzz.localservice.service.NormalBusi;
import org.hzz.localservice.service.SendSms;
import org.hzz.localservice.service.StockService;
import org.hzz.localservice.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ThreadCall {

    private ExecutorService executorService
            = Executors.newFixedThreadPool(2);

    @Autowired
    private NormalBusi normalBusi;
    @Autowired
    private StockService stockService;
    @Autowired
    private SendSms sendSms;

    public void processOrder() {
        long start = System.currentTimeMillis();
       //其他业务工作
        normalBusi.business();
        executorService.execute(new StockTask(stockService));
        executorService.execute(new SmsTask(sendSms));
        System.out.println("共耗时："+(System.currentTimeMillis()-start)+"ms");
    }

    private static class StockTask implements Runnable{

        private StockService stockService;

        public StockTask(StockService stockService) {
            this.stockService = stockService;
        }

        @Override
        public void run() {
            /*扣减库存*/
            stockService.addStock("A001",1000);
            stockService.deduceStock("B002",50);
        }
    }

    private static class SmsTask implements Runnable{

        private SendSms sendSms;

        public SmsTask(SendSms sendSms) {
            this.sendSms = sendSms;
        }

        @Override
        public void run() {
            /*发送邮件*/
            UserInfo userInfo = new UserInfo("Mark","Mark@xiangxue.com");
            System.out.println("Send mail: "+ sendSms.sendMail(userInfo));
        }
    }
}
