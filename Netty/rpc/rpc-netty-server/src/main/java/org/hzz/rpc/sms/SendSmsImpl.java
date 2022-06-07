package org.hzz.rpc.sms;

import org.hzz.remote.SendSms;
import org.hzz.remote.vo.UserInfo;

public class SendSmsImpl implements SendSms {
    @Override
    public boolean sendMail(UserInfo user) {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("已发送短信息给："+user.getName()+"到【"+user.getPhone()+"】");
        return true;
    }
}
