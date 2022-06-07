package org.hzz.msgpack.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import org.hzz.msgpack.domain.User;
import org.hzz.msgpack.domain.UserContact;

import java.util.concurrent.atomic.AtomicInteger;

public class ClientBusiHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private final int sendNumber;
    private AtomicInteger counter = new AtomicInteger(0);
    public ClientBusiHandler(int sendNumber){this.sendNumber = sendNumber;}


    /*** 客户端被通知channel活跃后，做事*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        User[] users = makeUsers();
        //发送数据
        for(User user:users){
            System.out.println("Send user:"+user);
            ctx.write(user);
        }
        ctx.flush();
    }

    /*** 发生异常后的处理*/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /*生成用户实体类的数组，以供发送*/
    private User[] makeUsers(){
        User[] users=new User[sendNumber];
        User user =null;
        for(int i=0;i<sendNumber;i++){
            user=new User();
            user.setAge(i);
            String userName = "ABCDEFG --->"+i;
            user.setUserName(userName);
            user.setId("No:"+(sendNumber-i));
            user.setUserContact(
                    new UserContact(userName+"@xiangxue.com","133"));
            users[i]=user;
        }
        return users;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("client Accept["+msg.toString(CharsetUtil.UTF_8)
                +"] and the counter is:"+counter.incrementAndGet());
    }
}
