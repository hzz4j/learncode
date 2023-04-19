package org.hzz;

import io.grpc.stub.StreamObserver;
import org.hzz.grpc.api.Buyer;
import org.hzz.grpc.api.Order;
import org.hzz.grpc.api.OrderQueryGrpc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrderQueryServiceImpl extends OrderQueryGrpc.OrderQueryImplBase{
    /**
     * mock一批数据
     * @return
     */
    private static List<Order> mockOrders(){
        List<Order> list = new ArrayList<>();
        Order.Builder builder = Order.newBuilder();

        for (int i = 0; i < 10; i++) {
            list.add(builder
                    .setOrderId(i)
                    .setProductId(1000+i)
                    .setOrderTime(System.currentTimeMillis()/1000)
                    .setBuyerRemark(("remark-" + i))
                    .build());
        }

        return list;
    }

    @Override
    public void listOrders(Buyer request, StreamObserver<Order> responseObserver) {
        // 持续输出到client
        for (Order order : mockOrders()) {
            try {
                TimeUnit.SECONDS.sleep(5); // 模拟耗时操作
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            responseObserver.onNext(order);
        }
        // 结束输出
        responseObserver.onCompleted();
    }

}
