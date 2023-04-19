package org.hzz;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GRPCServer {
    private static final int port = 50051;

    public static void main(String[] args) throws IOException, InterruptedException {
        //设置service端口
        ////创建server对象,监听端口,注册服务并启动
        Server server = ServerBuilder.forPort(port)//监听50051端口
                .addService(new OrderQueryServiceImpl())//注册服务
                .build()//创建Server对象
                .start();//启动
        System.out.println(String.format("GRpc服务端启动成功, 端口号: %d.", port));

        //我们将调用 awaittermination() 以保持服务器在后台保持运行。
        server.awaitTermination();
    }
}
