package org.hzz.methodoverload;

public abstract class ChannelInitializer<C extends Channel> {
    private boolean initChannel(ChannelHandlerContext ctx) throws Exception{
        this.initChannel((C)ctx);
        System.out.println("本身类1");
        return false;
    }

    protected abstract boolean initChannel(C ch) throws Exception;


    public void test(ChannelHandlerContext ctx) throws Exception {
        this.initChannel(ctx);
    }
}
