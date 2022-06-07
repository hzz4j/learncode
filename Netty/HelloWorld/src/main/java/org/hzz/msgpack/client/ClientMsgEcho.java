package org.hzz.msgpack.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;
import org.hzz.msgpack.server.ServerMsgPackEcho;

import java.net.InetSocketAddress;

public class ClientMsgEcho {
    private final String host;

    public ClientMsgEcho(String host) {
        this.host = host;
    }
    public static void main(String[] args) throws InterruptedException {
        new ClientMsgEcho("127.0.0.1").start();
    }

    private void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();/*线程组*/
        try {
            final Bootstrap b = new Bootstrap();;/*客户端启动必须*/
            b.group(group)/*将线程组传入*/
                    .channel(NioSocketChannel.class)/*指定使用NIO进行网络传输*/
                    /*配置要连接服务器的ip地址和端口*/
                    .remoteAddress(
                            new InetSocketAddress(host, ServerMsgPackEcho.PORT))
                    .handler(new ChannelInitializerImp());
            ChannelFuture f = b.connect().sync();
            System.out.println("已连接到服务器.....");
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    private static class ChannelInitializerImp extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            /*告诉netty，计算一下报文的长度，然后作为报文头加在前面*/
            ch.pipeline().addLast(new LengthFieldPrepender(2));
            /*对服务器的应答也要解码，解决粘包半包*/
            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));

            /*对我们要发送的数据做编码-序列化*/
            ch.pipeline().addLast(new MsgPackEncode());
            ch.pipeline().addLast(new ClientBusiHandler(5));
        }
    }
}
