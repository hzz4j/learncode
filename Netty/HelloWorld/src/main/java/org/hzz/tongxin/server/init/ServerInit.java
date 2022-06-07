package org.hzz.tongxin.server.init;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.hzz.tongxin.kryocodec.KryoDecoder;
import org.hzz.tongxin.kryocodec.KryoEncoder;
import org.hzz.tongxin.server.asyncpro.DefaultTaskProcessor;

public class ServerInit extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        // 性能监控
        p.addLast(new MetricsHandler());
        // 粘包半包
        p.addLast(new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
        p.addLast(new LengthFieldPrepender(2));

        //序列化
        p.addLast(new KryoDecoder());
        p.addLast(new KryoEncoder());

        // 处理心跳超时
        p.addLast(new ReadTimeoutHandler(15));
        p.addLast(new LoginAuthRespHandler());
        p.addLast(new HeartBeatRespHandler());
        p.addLast(new ServerBusiHandler(new DefaultTaskProcessor()));
    }
}
