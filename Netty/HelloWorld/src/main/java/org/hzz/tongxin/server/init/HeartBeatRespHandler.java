package org.hzz.tongxin.server.init;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.util.ReferenceCountUtil;
import org.hzz.tongxin.vo.MessageType;
import org.hzz.tongxin.vo.MsgHeader;
import org.hzz.tongxin.vo.MyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(HeartBeatRespHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MyMessage message = (MyMessage) msg;
        /*是不是心跳请求*/
        if(message.getMyHeader()!=null
                &&message.getMyHeader().getType()== MessageType.HEARTBEAT_REQ.value()) {
            /*心跳应答报文*/
            MyMessage heartBeatResp = buildHeatBeat();
            LOG.debug("心跳应答： "+ heartBeatResp);
            ctx.writeAndFlush(heartBeatResp);
            ReferenceCountUtil.release(msg);
        }else{
            super.channelRead(ctx,msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(cause instanceof ReadTimeoutException){
            LOG.warn("客户端长时间未通信，可能已经宕机，关闭链路");
            SecurityCenter.removeLoginUser(ctx.channel().remoteAddress().toString());
            ctx.close();
        }else{
            super.exceptionCaught(ctx, cause);
        }
    }

    private MyMessage buildHeatBeat() {
        MyMessage message = new MyMessage();
        MsgHeader msgHeader = new MsgHeader();
        msgHeader.setType(MessageType.HEARTBEAT_RESP.value());
        message.setMyHeader(msgHeader);
        return message;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOG.warn("客户端已关闭连接");
        super.channelInactive(ctx);
    }
}
