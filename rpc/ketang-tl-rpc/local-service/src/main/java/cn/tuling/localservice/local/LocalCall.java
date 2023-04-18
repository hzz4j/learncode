package cn.tuling.localservice.local;


import cn.tuling.localservice.service.NormalBusi;
import cn.tuling.localservice.service.SendSms;
import cn.tuling.localservice.service.StockService;
import cn.tuling.localservice.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：本地方法调用的实现
 */
@Service
public class LocalCall {

    @Autowired
    private NormalBusi normalBusi;
    @Autowired
    private StockService stockService;
    @Autowired
    private SendSms sendSms;

    public void processOrder() {

        long start = System.currentTimeMillis();
        normalBusi.business();

        /*扣减库存*/
        stockService.addStock("A001",1000);
        stockService.deduceStock("B002",50);

        /*发送邮件*/
        UserInfo userInfo = new UserInfo("Mark","Mark@xiangxue.com");
        System.out.println("Send mail: "+ sendSms.sendMail(userInfo));

        System.out.println("共耗时："+(System.currentTimeMillis()-start)+"ms");
    }

}
