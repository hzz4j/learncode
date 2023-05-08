package org.hzz.threadquestion.curator;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.listen.Listenable;
import org.apache.curator.framework.state.ConnectionStateListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class MyConnectionStateManager {
    private final ExecutorService service;
    private List<ConnectionStateListener> listeners = new ArrayList<>();


    public MyConnectionStateManager(){
        service = Executors.newSingleThreadExecutor();
    }

    public void start(){
        service.submit(
                new Callable<Object>() {
                    @Override
                    public Object call() throws Exception{
                        processEvent();
                        return null;
                    }
                }
        );
    }

    private void processEvent(){
        Scanner scanner = new Scanner(System.in);
        while(true){
//            System.out.println("信号发生");
            log.info("信号发生");
            scanner.next();
            this.listeners.forEach(listener -> listener.stateChanged(null,null));
        }
    }

    public List<ConnectionStateListener> getListenable()
    {
        return listeners;
    }
}
