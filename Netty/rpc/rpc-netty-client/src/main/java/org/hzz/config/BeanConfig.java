package org.hzz.config;

import org.hzz.remote.SendSms;
import org.hzz.rpc.RpcClientFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Autowired
    private RpcClientFrame rpcClientFrame;

    @Bean
    public SendSms sendSms() throws Exception {
        SendSms sendSms = rpcClientFrame.getRemoteProxyObject(SendSms.class);
        return sendSms;
    }
}
