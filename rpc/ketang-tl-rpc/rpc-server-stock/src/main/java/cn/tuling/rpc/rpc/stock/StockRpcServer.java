package cn.tuling.rpc.rpc.stock;

import cn.tuling.rpc.rpc.base.RpcServerFrame;
import cn.tuling.rpc.remote.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;

/**
 * 类说明：rpc的服务端，提供服务
 */
@Service
public class StockRpcServer {

    @Autowired
    private RpcServerFrame rpcServerFrame;

    @PostConstruct
    public void server() throws Throwable {
        Random r = new Random();
        int port = r.nextInt(100)+7778;
        rpcServerFrame.startService(StockService.class.getName(),
                "127.0.0.1",port,StockServiceImpl.class);

    }

}
