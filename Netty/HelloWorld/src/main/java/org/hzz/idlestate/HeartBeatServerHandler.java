package org.hzz.idlestate;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;


@Slf4j
public class HeartBeatServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("收到客户端消息：" + msg);
        if ("HeartBeat Packet".equals(msg)) {
            ctx.writeAndFlush("Server OK");
        }else{
            log.info("收到客户端消息：" + msg);
        }

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:
                    log.info("读空闲, 关闭连接");
                    closeChannel(ctx);
                case WRITER_IDLE:
                    log.info("写空闲");
                    break;
                case ALL_IDLE:
                    log.info("读写空闲");
                    break;
            }
        }
    }

    private void closeChannel(ChannelHandlerContext ctx){
        SocketAddress loc = ctx.channel().localAddress();
        SocketAddress rem = ctx.channel().remoteAddress();
        ctx.writeAndFlush("idle close");
        ctx.channel().close().addListener(future -> {
            if (future.isSuccess()) {
                log.info("关闭连接成功: loc={}, rem={}", loc, rem);
            } else {
                log.info("关闭连接失败: loc={}, rem={}", loc, rem);
            }
        });
    }
}
