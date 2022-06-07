package org.hzz.tongxin.server.init;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.hzz.tongxin.vo.MessageType;
import org.hzz.tongxin.vo.MsgHeader;
import org.hzz.tongxin.vo.MyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * 认证检查
 */
public class LoginAuthRespHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(LoginAuthRespHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MyMessage message = (MyMessage) msg;
        MyMessage loginResp = null;
        if(message.getMyHeader()!=null
                && message.getMyHeader().getType() == MessageType.LOGIN_REQ.value()){
            LOG.info("收到客户端认证请求 : " + message);
            String nodeIndex = ctx.channel().remoteAddress().toString();
            /* 重复登陆，拒绝，这里用客户端的地址代替了实际的用户信息*/
            if(SecurityCenter.isDupLog(nodeIndex)){
                loginResp = buildResponse((byte) -1);
                LOG.warn("拒绝重复登录，应答消息 : " + loginResp);
                ctx.writeAndFlush(loginResp);
                ctx.close();
            }else{
                /*检查用户是否在白名单中，在则允许登录，并写入缓存*/
                InetSocketAddress address = (InetSocketAddress) ctx.channel()
                        .remoteAddress();
                String ip = address.getAddress().getHostAddress();
                if(SecurityCenter.isWhiteIP(ip)){
                    SecurityCenter.addLoginUser(nodeIndex);
                    loginResp = buildResponse((byte) 0);
                    LOG.info("认证通过，应答消息 : " + loginResp);
                    ctx.writeAndFlush(loginResp);
                }else{
                    loginResp = buildResponse((byte) -1);
                    LOG.warn("认证失败，应答消息 : " + loginResp);
                    ctx.writeAndFlush(loginResp);
                    ctx.close();
                }
            }
            ReferenceCountUtil.release(msg);
        }else{
            super.channelRead(ctx,msg);
        }

    }

    private MyMessage buildResponse(byte result) {
        MyMessage message = new MyMessage();
        MsgHeader msgHeader = new MsgHeader();
        msgHeader.setType(MessageType.LOGIN_RESP.value());
        message.setMyHeader(msgHeader);
        message.setBody(result);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        // 删除缓存
        SecurityCenter.removeLoginUser(ctx.channel().remoteAddress().toString());
        ctx.close();
    }
}
