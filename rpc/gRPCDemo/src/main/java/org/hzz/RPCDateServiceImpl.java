package org.hzz;

import io.grpc.stub.StreamObserver;
import org.hzz.grpc.RPCDateServiceGrpc;
import org.hzz.grpc.RPCDateRequest;
import org.hzz.grpc.RPCDateResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RPCDateServiceImpl extends RPCDateServiceGrpc.RPCDateServiceImplBase {
    @Override
    public void getDate(RPCDateRequest request, StreamObserver<RPCDateResponse> responseObserver) {
        RPCDateResponse response = null;

        String userName = request.getUserName();
        String data = String.format("你好:%s,今天是%s.", userName, LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        try{
            response = RPCDateResponse.newBuilder()
                    .setServerDate(data)
                    .build();
//            int i = 3/0;
        }catch (Exception e){
            responseObserver.onError(e);
        } finally {
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }
}
