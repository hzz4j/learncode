package org.hzz.msgpack.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.net.InetSocketAddress;

public class ServerMsgPackEcho {
    public static final int PORT = 9995;
    public static void main(String[] args) throws InterruptedException {
        ServerMsgPackEcho serverMsgPackEcho = new ServerMsgPackEcho();
        System.out.println("服务器即将启动");
        serverMsgPackEcho.start();
    }

    private void start() throws InterruptedException{
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(eventLoopGroup)
                    .localAddress(new InetSocketAddress(PORT))
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializerImp());
            ChannelFuture f = serverBootstrap.bind().sync();
            System.out.println("服务器启动完成，等待客户端的连接和数据.....");
            f.channel().closeFuture().sync();/*阻塞直到服务器的channel关闭*/
        }finally {
            eventLoopGroup.shutdownGracefully().sync();/*优雅关闭线程组*/
        }
    }

    private static class ChannelInitializerImp extends ChannelInitializer<Channel> {
        private static BusiServerHandler busiServerHandler = new BusiServerHandler();
        @Override
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535,
                    0,2,0,
                    2));
            ch.pipeline().addLast(new MsgPackDecoder());
//            ch.pipeline().addLast(busiServerHandler);
            ch.pipeline().addLast(new BusiServerHandler());
        }
    }
}
