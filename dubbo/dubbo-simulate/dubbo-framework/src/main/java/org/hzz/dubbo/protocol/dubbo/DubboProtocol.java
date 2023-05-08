package org.hzz.dubbo.protocol.dubbo;

import org.hzz.dubbo.Invocation;
import org.hzz.dubbo.Protocol;
import org.hzz.dubbo.URL;

public class DubboProtocol implements Protocol {
    // 启动一个Netty服务端
    @Override
    public void start(URL url) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(url.getHostname(), url.getPort());

    }

    // 消费这每次请求的服务的时候临时创建一个Netty客户端
    @Override
    public String send(URL url, Invocation invocation) {
        NettyClient nettyClient = new NettyClient();
        return nettyClient.send(url.getHostname(),url.getPort(), invocation);
    }
}
