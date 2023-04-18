package cn.tuling.rpc.rpc.sms;


import cn.tuling.rpc.remote.vo.UserInfo;
import cn.tuling.rpc.remote.SendSms;

/**
 *@author Mark老师
 *
 *类说明：短信息发送服务的实现
 */
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
