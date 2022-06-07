package org.hzz.tongxin.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.hzz.tongxin.server.init.ServerInit;
import org.hzz.tongxin.vo.NettyConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServer {
    private static final Logger LOG = LoggerFactory.getLogger(NettyServer.class);
    public static void main(String[] args) throws Exception {
        new NettyServer().bind();
    }

    private void bind() throws InterruptedException {
        // 配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1,new DefaultThreadFactory("boss"));
        EventLoopGroup workerGroup = new NioEventLoopGroup(NettyRuntime.availableProcessors(),
                new DefaultThreadFactory("nt_worker"));

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ServerInit());
        // 绑定端口，同步等待成功
        b.bind(NettyConstant.SERVER_PORT).sync();
        LOG.info("Netty server start : "
                + (NettyConstant.SERVER_IP + " : "
                + NettyConstant.SERVER_PORT));


    }
}
