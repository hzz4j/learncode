package org.hzz.msgpack.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.hzz.msgpack.domain.User;

import java.util.concurrent.atomic.AtomicInteger;

@ChannelHandler.Sharable
public class BusiServerHandler extends ChannelInboundHandlerAdapter {
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //将上一个handler生成的数据强制转型
        User user = (User)msg;
        System.out.println("Server Accept["+user
                +"] and the counter is:"+counter.incrementAndGet());
        //服务器的应答
        String resp = "I process user :"+user.getUserName()
                + System.getProperty("line.separator");
        ctx.writeAndFlush(Unpooled.copiedBuffer(resp.getBytes()));
        ctx.fireChannelRead(user);
    }

    /*** 发生异常后的处理*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
