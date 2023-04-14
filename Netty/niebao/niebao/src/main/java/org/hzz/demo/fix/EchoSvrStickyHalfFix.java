package org.hzz.demo.fix;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import org.hzz.demo.EchoSvrSHHandler;
import org.hzz.demo.EchoSvrStickyHalf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 修复半包处理
 */
public class EchoSvrStickyHalfFix {
    private static final Logger LOG = LoggerFactory.getLogger(EchoSvrStickyHalf.class);

    private final int port;

    public EchoSvrStickyHalfFix(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 9999;
        EchoSvrStickyHalfFix echoSvrStickyHalf = new EchoSvrStickyHalfFix(port);
        LOG.info("服务器即将启动");
        echoSvrStickyHalf.start();
        LOG.info("服务器关闭");
    }

    public void start() throws InterruptedException {
        /*线程组*/
        EventLoopGroup group  = new NioEventLoopGroup();
        try {
            /*服务端启动必备*/
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 处理半包 、粘包 \r\n
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new EchoSvrSHHandler());
                        }
                    });

            /*异步绑定到服务器，sync()会阻塞到完成*/
            ChannelFuture f = b.bind().sync();
            LOG.info("服务器启动完成1。");
            /*阻塞当前线程，直到服务器的ServerChannel被关闭*/
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
