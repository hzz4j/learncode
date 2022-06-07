package org.hzz.tongxin.client.init;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.util.ReferenceCountUtil;
import org.hzz.tongxin.vo.MessageType;
import org.hzz.tongxin.vo.MsgHeader;
import org.hzz.tongxin.vo.MyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端在长久未向服务器业务请求时，发出心跳请求报文
 */
public class HearBeatReqHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(HearBeatReqHandler.class);


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MyMessage message = (MyMessage) msg;
        /*是不是心跳的应答*/
        if(message.getMyHeader()!=null
                &&message.getMyHeader().getType()==MessageType.HEARTBEAT_RESP.value()){
            LOG.debug("收到服务器心跳应答，服务器正常");
            ReferenceCountUtil.release(msg);
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            MyMessage heartBeat = buildHeatBeat();
            LOG.debug("写空闲，发出心跳报文维持连接： "+ heartBeat);
            ctx.writeAndFlush(heartBeat);
        }else{
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(cause instanceof ReadTimeoutException){
            LOG.warn("服务器长时间未应答，关闭链路");
            //ctx.close();
        }
        super.exceptionCaught(ctx, cause);
    }

    private MyMessage buildHeatBeat() {
        MyMessage message = new MyMessage();
        MsgHeader msgHeader = new MsgHeader();
        msgHeader.setType(MessageType.HEARTBEAT_REQ.value());
        message.setMyHeader(msgHeader);
        return message;
    }
}
