package cn.tuling.rpcnetty.config;


import cn.tuling.rpcnetty.remote.SendSms;
import cn.tuling.rpcnetty.rpc.RpcClientFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类说明：
 */
@Configuration
public class BeanConfig {

    @Autowired
    private RpcClientFrame rpcClientFrame;

    @Bean
    public SendSms getSmsService() throws Exception{
        return rpcClientFrame.getRemoteProxyObject(SendSms.class);
    }
}
