package org.hzz.rpc.rpc.sms;

import org.hzz.rpc.remote.SendSms;
import org.hzz.rpc.rpc.base.RpcServerFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;

/**
 * 类说明：rpc的服务端，提供服务
 */
@Service
public class SmsRpcServer {

    @Autowired
    private RpcServerFrame rpcServerFrame;

    @PostConstruct
    public void server() throws Throwable {
        Random r = new Random();
        int port = 8778+r.nextInt(100);
        rpcServerFrame.startService(SendSms.class.getName(),
                "127.0.0.1",port,SendSmsImpl.class);

    }

}
