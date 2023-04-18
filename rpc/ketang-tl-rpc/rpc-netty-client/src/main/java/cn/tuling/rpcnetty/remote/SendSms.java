package cn.tuling.rpcnetty.remote;


import cn.tuling.rpcnetty.remote.vo.UserInfo;

/**
 *@author Mark老师
 *
 *类说明：短信息发送接口
 */
public interface SendSms {
    /*发送短信*/
    boolean sendMail(UserInfo user);

}
