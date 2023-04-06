package org.hzz.executor;

import org.springframework.stereotype.Component;

public class SimpleExecutor implements Executor{

    @Override
    public void executor() {
        System.out.println("SimpleExecutor");
    }
    
}
