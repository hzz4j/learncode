package org.hzz.methodoverload;

public class Main {
    public static void main(String[] args) throws Exception {
        test(new ChannelInitializer<ChannelHandlerContext>() {
            @Override
            protected boolean initChannel(ChannelHandlerContext ch) throws Exception {
                System.out.println("我运行了abstact");
                return false;
            }
        });
    }

    public static void test(ChannelInitializer channelInitializer) throws Exception {
        channelInitializer.test(new ChannelHandlerContext());
    }
}
