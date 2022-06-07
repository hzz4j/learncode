package org.hzz.tongxin.server.init;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.ImmediateEventExecutor;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import org.junit.internal.runners.statements.RunAfters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class MetricsHandler extends ChannelDuplexHandler {
    private static final Logger LOG = LoggerFactory.getLogger(MetricsHandler.class);

    private static AtomicLong chCount = new AtomicLong(0);
    private static AtomicBoolean startTask = new AtomicBoolean(false);
    private static AtomicLong totalReadBytes = new AtomicLong(0);
    private static AtomicLong totalWriteBytes = new AtomicLong(0);
    private static ScheduledExecutorService statService = new ScheduledThreadPoolExecutor(1);
    /*ChannelGroup用来保存所有已经连接的Channel*/
    private final static ChannelGroup channelGroup = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        chCount.incrementAndGet();
        if(startTask.compareAndSet(false,true)){
            LOG.info("----------------性能指标采集激活-------------------");
            statService.scheduleAtFixedRate(createMetricTask(ctx),
                    0,10*1000, TimeUnit.MILLISECONDS);
        }

        channelGroup.add(ctx.channel());
        super.channelActive(ctx);
    }


    private Runnable createMetricTask(ChannelHandlerContext ctx){
        return ()->{
            LOG.info("----------------性能指标采集开始-------------------");
            /*目前有多少在线Channel*/
            LOG.info("目前在线Channel数：" + chCount.get());

            /*I/O线程池待处理队列大小*/
            Iterator<EventExecutor> executorGroups = ctx.executor().parent().iterator();
            while (executorGroups.hasNext()) {
                SingleThreadEventExecutor executor =
                        (SingleThreadEventExecutor) executorGroups.next();
                int size = executor.pendingTasks();
                if (executor == ctx.executor())
                    LOG.info(ctx.channel() + ":" + executor + "待处理队列大小 :  " + size);
                else
                    LOG.info(executor + " 待处理队列大小 : " + size);
            }

            /*发送队列积压字节数*/
            Iterator<Channel> channels = channelGroup.iterator();
            while(channels.hasNext()){
                Channel channel = channels.next();
                if(channel instanceof ServerChannel) continue;
                LOG.info(channel+"发送缓存积压字节数："+channel.unsafe().outboundBuffer().totalPendingWriteBytes());
            }
            LOG.info( "读取速率(字节/分)："+totalReadBytes.getAndSet(0));
            LOG.info( "写出速率(字节/分)："+totalWriteBytes.getAndSet(0));

            LOG.info("----------------性能指标采集结束-------------------");
        };
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        int readableBytes = ((ByteBuf)msg).readableBytes();
        totalReadBytes.getAndAdd(readableBytes) ;
        ctx.fireChannelRead(msg) ;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        int writeableBytes = ((ByteBuf)msg).readableBytes();
        totalWriteBytes.getAndAdd(writeableBytes) ;
        super.write(ctx, msg, promise);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        chCount.decrementAndGet();
        channelGroup.remove(ctx.channel());
        super.channelInactive(ctx);
    }
}
