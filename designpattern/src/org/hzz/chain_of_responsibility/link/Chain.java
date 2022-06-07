package org.hzz.chain_of_responsibility.link;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Chain {
    private Context header;
    private Context currentContext;

    public void addLast(Handler handler){
        Context context = new Context(handler);
        buildChain(context);
    }

    private void buildChain(Context context){
        if(Objects.isNull(currentContext)){
            currentContext = context;
            header = context;
        }else{
            currentContext.setContext(context);
            currentContext = context;
        }
    }

    public void start(Request request){
        if(Objects.nonNull(header)){
            header.process(request);
        }
    }
}
