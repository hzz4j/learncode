package org.hzz.idlestate;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (msg != null && msg.equals("idle close")) {
            ctx.channel().close().addListener(future -> {
                if (future.isSuccess()) {
                    log.info("服务器主动关闭连接,客户端也关闭连接");
                } else {
                    log.info("关闭连接失败");
                }
            });
        }
    }
}
