package org.hzz.methodoverload.test;


public class TestMain {

    // 重载测试
    public static abstract class ChannelInitializer<C extends Channel> {
        private boolean initChannel(ChannelHandlerContext ctx) throws Exception{
            ctx.run("本身方法");
            // 调用实现的方法
            this.initChannel((C)ctx);
            return false;
        }

        // 子类实现为protected  boolean initChannel(ChannelHandlerContext ctx)
        protected abstract boolean initChannel(C ctx) throws Exception;
    }

    public static void main(String[] args) throws Exception {
        test(new TestMain.ChannelInitializer<ChannelHandlerContext>(){
            @Override
            protected boolean initChannel(ChannelHandlerContext ctx) throws Exception {
                ctx.run("实现类方法重载");
                return false;
            }
        });
    }

    public static void test(ChannelInitializer channelInitializer) throws Exception {
        ChannelHandlerContext channelHandlerContext = new ChannelHandlerContext();
        System.out.println("-----------------会调用谁呢------------------------------");
        channelInitializer.initChannel(channelHandlerContext);
    }


    public interface Channel {
        void run(String name);
    }

    public static class ChannelHandlerContext implements Channel {
        @Override
        public void run(String name) {
            System.out.println("run: " + name);
        }
    }
}

/**
 * -----------------会调用谁呢------------------------------
 * run: 本身方法
 * run: 实现类方法重载
 */