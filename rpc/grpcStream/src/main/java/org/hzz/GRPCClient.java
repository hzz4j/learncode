package org.hzz;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.hzz.grpc.api.Buyer;
import org.hzz.grpc.api.Order;
import org.hzz.grpc.api.OrderQueryGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class GRPCClient {
    private static final Logger logger = LoggerFactory.getLogger(GRPCClient.class);
    private static final String host = "localhost";
    private static final int serverPort = 50051;
    public static void main(String[] args) {
        //1,拿到一个通信channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, serverPort).
                usePlaintext()////这里将使用纯文本，无需任何加密
                .build();
        try {

            //2.拿到stub对象
            OrderQueryGrpc.OrderQueryBlockingStub orderQueryBlockingStub = OrderQueryGrpc.newBlockingStub(channel);
            // 我们可以使用newBuilder 来设置 RPCDateRequest 对象的用户名属性。并得到从服务器返回的 RPCDateResponse 对象。
            // gRPC的请求参数
            Buyer buyer = Buyer.newBuilder().setBuyerId(101).build();

            // 通过stub发起远程gRPC请求

            // gRPC的响应
            Iterator<Order> orderIterator;
            orderIterator = orderQueryBlockingStub.listOrders(buyer);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            while (orderIterator.hasNext()) {
                Order order = orderIterator.next();
                logger.info("订单ID：{}产品ID：{}日期：{}备注：{}",
                        order.getOrderId(),
                        order.getProductId(),
                        // 使用DateTimeFormatter将时间戳转为字符串
                        dtf.format(LocalDateTime.ofEpochSecond(order.getOrderTime(), 0, ZoneOffset.of("+8"))),
                        order.getBuyerRemark());
            }
        } finally {
            // 5.关闭channel, 释放资源.
            channel.shutdown();
        }

    }
}
/**
 * 2023-04-19 16:38:21.729 [INFO ] org.hzz.GRPCClient [main] : 订单ID：0产品ID：1000日期：2023-04-19 16:38:16备注：remark-0
 * 2023-04-19 16:38:26.717 [INFO ] org.hzz.GRPCClient [main] : 订单ID：1产品ID：1001日期：2023-04-19 16:38:16备注：remark-1
 * 2023-04-19 16:38:31.725 [INFO ] org.hzz.GRPCClient [main] : 订单ID：2产品ID：1002日期：2023-04-19 16:38:16备注：remark-2
 * 2023-04-19 16:38:36.734 [INFO ] org.hzz.GRPCClient [main] : 订单ID：3产品ID：1003日期：2023-04-19 16:38:16备注：remark-3
 * 2023-04-19 16:38:41.735 [INFO ] org.hzz.GRPCClient [main] : 订单ID：4产品ID：1004日期：2023-04-19 16:38:16备注：remark-4
 * 2023-04-19 16:38:46.749 [INFO ] org.hzz.GRPCClient [main] : 订单ID：5产品ID：1005日期：2023-04-19 16:38:16备注：remark-5
 * 2023-04-19 16:38:51.758 [INFO ] org.hzz.GRPCClient [main] : 订单ID：6产品ID：1006日期：2023-04-19 16:38:16备注：remark-6
 * 2023-04-19 16:38:56.759 [INFO ] org.hzz.GRPCClient [main] : 订单ID：7产品ID：1007日期：2023-04-19 16:38:16备注：remark-7
 * 2023-04-19 16:39:01.766 [INFO ] org.hzz.GRPCClient [main] : 订单ID：8产品ID：1008日期：2023-04-19 16:38:16备注：remark-8
 * 2023-04-19 16:39:06.767 [INFO ] org.hzz.GRPCClient [main] : 订单ID：9产品ID：1009日期：2023-04-19 16:38:16备注：remark-9
 */