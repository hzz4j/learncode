package org.hzz.chain_of_responsibility.simple.handler;

import org.hzz.chain_of_responsibility.simple.Request;

public class LoggingHandler extends Handler{
    public LoggingHandler(Handler next) {
        super( next );
    }

    @Override
    public boolean process(Request request) {
        System.out.println(" 登录验证");
        if (request.isLoggedOn()){
            Handler next=getNext();
            if (null==next){
                return true;
            }
            if (!next.process( request )) {
                return false;
            }else{
                return true;
            }
        }
        return false;
    }
}
