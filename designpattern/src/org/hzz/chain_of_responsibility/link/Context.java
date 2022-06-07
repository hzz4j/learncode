package org.hzz.chain_of_responsibility.link;


import java.util.Objects;

public class Context{
    private Handler handler;
    private Context next;

    public Context(Handler handler){
        this.handler = handler;
    }

    public void setContext(Context context){
        this.next = context;
    }

    public void process(Request request) {
        handler.process(request);
        if(handler.isSuccess()){
            if(Objects.nonNull(next)){
                 next.process(request);
            }
        }else{
            handler.failInfo();
        }
    }
}
