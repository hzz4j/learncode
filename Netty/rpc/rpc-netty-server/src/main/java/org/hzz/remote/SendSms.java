package org.hzz.remote;

import org.hzz.remote.vo.UserInfo;

/**
 * 短信息发送接口
 */
public interface SendSms {
    /*发送短信*/
    boolean sendMail(UserInfo user);
}
