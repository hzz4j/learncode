package cn.tuling.rpcnetty.rpc.base.server;


import cn.tuling.rpcnetty.rpc.base.vo.MessageType;
import cn.tuling.rpcnetty.rpc.base.vo.MyHeader;
import cn.tuling.rpcnetty.rpc.base.vo.MyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Mark老师
 * 类说明：心跳
 */
public class HeartBeatRespHandler extends ChannelInboundHandlerAdapter {

	private static final Log LOG
			= LogFactory.getLog(HeartBeatRespHandler.class);

    public void channelRead(ChannelHandlerContext ctx, Object msg)
	    throws Exception {
		MyMessage message = (MyMessage) msg;
		// 返回心跳应答消息
		if (message.getMyHeader() != null
			&& message.getMyHeader().getType() == MessageType.HEARTBEAT_REQ
				.value()) {
//			LOG.info("Receive client heart beat message : ---> "+ message);
			MyMessage heartBeat = buildHeatBeat();
//			LOG.info("Send heart beat response message to ---> client");
			ctx.writeAndFlush(heartBeat);
			ReferenceCountUtil.release(msg);
		} else
			ctx.fireChannelRead(msg);
    }

    private MyMessage buildHeatBeat() {
		MyMessage message = new MyMessage();
		MyHeader myHeader = new MyHeader();
		myHeader.setType(MessageType.HEARTBEAT_RESP.value());
		message.setMyHeader(myHeader);
		return message;
    }

}
