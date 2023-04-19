package org.hzz;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.hzz.grpc.RPCDateRequest;
import org.hzz.grpc.RPCDateResponse;
import org.hzz.grpc.RPCDateServiceGrpc;

public class GRPCClient {
    private static final String host = "localhost";
    private static final int serverPort = 9999;


    public static void main(String[] args) {
        //1,拿到一个通信channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, serverPort)
                .usePlaintext() // 使用明文传输
                .build();

        try {

            //2,通过channel拿到一个stub
            RPCDateServiceGrpc.RPCDateServiceBlockingStub stub = RPCDateServiceGrpc.newBlockingStub(channel);

            RPCDateRequest request = RPCDateRequest.newBuilder().setUserName("Q10Viking").build();
            //3,请求
            RPCDateResponse response = stub.getDate(request);
            //4 输出结果
            System.out.println(response.getServerDate());
        }finally {
            //5 关闭channel
            channel.shutdown();
        }
    }
}
/**
 * 你好:Q10Viking,今天是2023-04-19.
 */