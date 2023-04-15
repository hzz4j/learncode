package org.hzz.idlestate;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatClient {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try{
            bootstrap.group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .remoteAddress("localhost", 8080)
                    .handler(new ClientChannelInitializer());
            ChannelFuture connect = bootstrap.connect();
            connect.addListener(future -> {
                if (future.isSuccess()) {
                    log.info("连接成功");
                } else {
                    log.info("连接失败");
                }
            });
            connect.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bootstrap.group().shutdownGracefully();
        }
    }

    private static class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline()
                    .addLast(new StringEncoder())
                    .addLast(new StringDecoder())
                    .addLast(new HeartBeatClientHandler());
        }
    }
}

