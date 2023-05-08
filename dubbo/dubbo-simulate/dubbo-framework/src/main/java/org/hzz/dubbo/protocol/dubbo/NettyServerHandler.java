package org.hzz.dubbo.protocol.dubbo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.hzz.dubbo.Invocation;
import org.hzz.dubbo.register.LocalRegister;
import java.lang.reflect.Method;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Invocation invocation = (Invocation) msg;

        Class serviceImpl = LocalRegister.get(invocation.getInterfaceName());

        Method method = serviceImpl.getMethod(invocation.getMethodName(), invocation.getParamTypes());
        Object result = method.invoke(serviceImpl.newInstance(), invocation.getParams());

        System.out.println("Netty-------------" + result.toString());
        ctx.writeAndFlush("(Netty)" + result);
    }
}
