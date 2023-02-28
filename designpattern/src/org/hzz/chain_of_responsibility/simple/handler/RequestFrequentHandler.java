package org.hzz.chain_of_responsibility.simple.handler;

import org.hzz.chain_of_responsibility.simple.Request;

public class RequestFrequentHandler extends Handler{
    public RequestFrequentHandler(Handler handler){
        super(handler);
    }
    @Override
    public boolean process(Request request) {
        System.out.println("访问频率控制.");
        if (request.isFrequentOk()){
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
