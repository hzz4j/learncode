package org.hzz.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class EchoServer {
    private static final Logger LOG = LoggerFactory.getLogger(EchoServer.class);

    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 9998;
        EchoServer echoServer = new EchoServer(port);
        LOG.info("服务器即将启动");
        echoServer.start();
        LOG.info("服务器关闭");
    }

    private void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
//        EchoServerHandler echoServerHandler = new EchoServerHandler();
        try {
            serverBootstrap
                    .group(bossGroup,eventLoopGroup)
//                    .localAddress(new InetSocketAddress(port))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            /**绑定多个端口，同步等待成功*/
            ChannelFuture channelFuture1 = serverBootstrap.bind(new InetSocketAddress(10000)).sync();
//            ChannelFuture channelFuture2 = serverBootstrap.bind(new InetSocketAddress(10001)).sync();
//            ChannelFuture channelFuture3 = serverBootstrap.bind(new InetSocketAddress(10002)).sync();
            LOG.info(channelFuture1.channel().localAddress().toString());
            /*阻塞当前线程，直到服务器的ServerChannel被关闭*/
//            channelFuture2.channel().closeFuture().sync();
//            channelFuture3.channel().closeFuture().sync();
            channelFuture1.channel().closeFuture().sync();
            LOG.info("服务器关闭");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }

    }
}
