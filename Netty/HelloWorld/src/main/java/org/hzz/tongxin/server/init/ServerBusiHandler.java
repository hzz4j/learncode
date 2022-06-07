package org.hzz.tongxin.server.init;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.hzz.tongxin.server.asyncpro.AsyncBusiProcess;
import org.hzz.tongxin.server.asyncpro.ITaskProcessor;
import org.hzz.tongxin.util.EncryptUtils;
import org.hzz.tongxin.vo.MessageType;
import org.hzz.tongxin.vo.MsgHeader;
import org.hzz.tongxin.vo.MyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerBusiHandler extends SimpleChannelInboundHandler<MyMessage> {
    private static final Logger LOG = LoggerFactory.getLogger(ServerBusiHandler.class);
    private ITaskProcessor taskProcessor;
    
    public ServerBusiHandler(ITaskProcessor processor){
        super();
        this.taskProcessor = processor;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyMessage msg) throws Exception {
        /*检查MD5*/
        String headMd5 = msg.getMyHeader().getMd5();
        String calcMd5 = EncryptUtils.encryptObj(msg.getBody());
        if(!headMd5.equals(calcMd5)){
            LOG.error("报文md5检查不通过："+headMd5+" vs "+calcMd5+"，关闭连接");
            ctx.writeAndFlush(buildBusiResp("报文md5检查不通过，关闭连接"));
            ctx.close();
        }else {
            LOG.info(msg.toString());
            if (msg.getMyHeader().getType() == MessageType.ONE_WAY.value()) {
                LOG.debug("ONE_WAY类型消息，异步处理");
                AsyncBusiProcess.submitTask(taskProcessor.execAsyncTask(msg));
            } else {
                LOG.debug("TWO_WAY类型消息，应答");
                ctx.writeAndFlush(buildBusiResp("OK"));
            }
        }
    }

    private MyMessage buildBusiResp(String result) {
        MyMessage message = new MyMessage();
        MsgHeader msgHeader = new MsgHeader();
        msgHeader.setType(MessageType.SERVICE_RESP.value());
        message.setMyHeader(msgHeader);
        message.setBody(result);
        return message;
    }
}
