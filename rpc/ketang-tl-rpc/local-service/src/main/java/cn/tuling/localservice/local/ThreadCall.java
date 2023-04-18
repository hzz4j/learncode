package cn.tuling.localservice.local;

import cn.tuling.localservice.service.NormalBusi;
import cn.tuling.localservice.service.SendSms;
import cn.tuling.localservice.service.StockService;
import cn.tuling.localservice.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Mark老师   享学课堂 https://enjoy.ke.qq.com
 * 类说明：
 */
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
