package org.hzz.service;

import net.devh.boot.grpc.server.service.GrpcService;
import org.hzz.grpc.lib.SimpleGrpc;
import org.hzz.grpc.lib.HelloRequest;
import io.grpc.stub.StreamObserver;
import org.hzz.grpc.lib.HelloReply;
@GrpcService
public class GrpcServerService extends SimpleGrpc.SimpleImplBase {
    @Override
    public void sayHello( HelloRequest request,
                         StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
