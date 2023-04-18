package cn.tuling.localservice.service.impl;


import cn.tuling.localservice.service.SendSms;
import cn.tuling.localservice.vo.UserInfo;
import org.springframework.stereotype.Service;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：短信息发送服务的实现
 */
@Service
public class SendSmsImpl implements SendSms {

    @Override
    public boolean sendMail(UserInfo user) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发送短信息给："+user.getName()+"到【"+user.getPhone()+"】");
        return true;
    }
}
