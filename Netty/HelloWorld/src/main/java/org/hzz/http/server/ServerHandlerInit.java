package org.hzz.http.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;

public class ServerHandlerInit extends ChannelInitializer<Channel> {
    private final SslContext sslCtx;
    public ServerHandlerInit(SslContext sslCtx){
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline ph = ch.pipeline();
        if(sslCtx!=null){
            ph.addLast(sslCtx.newHandler(ch.alloc()));
        }
        /*把应答报文 编码*/
        ph.addLast("encoder",new HttpResponseEncoder());
        /*把请求报文 解码*/
        ph.addLast("decoder",new HttpRequestDecoder());


        /*聚合http为一个完整的报文*/
        ph.addLast("aggregator",
                new HttpObjectAggregator(10*1024*1024));
        /*把应答报文 压缩,非必要*/
        ph.addLast("compressor",new HttpContentCompressor());
        ph.addLast(new BusiHandler());
    }
}
