package org.hzz.service;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.hzz.grpc.lib.HelloReply;
import org.hzz.grpc.lib.HelloRequest;
import org.hzz.grpc.lib.SimpleGrpc;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {
    @GrpcClient("local-grpc-server")
    private SimpleGrpc.SimpleBlockingStub simpleStub;

    public String sendMessage(final String name) {
        try {
            final HelloReply response = this.simpleStub.sayHello(HelloRequest.newBuilder().setName(name).build());
            return response.getMessage();
        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }
}
