package org.hzz.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.logging.Logger;

public class Reactor implements Runnable{
    protected static Logger logger = Logger.getLogger(Reactor.class.getName());
    private Selector selector;

    public Reactor() throws IOException {
        this.selector = Selector.open();
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                doSelect();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    dispatch(key);
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    protected int doSelect() throws IOException{
        return selector.select();
    }

    protected void dispatch(SelectionKey key){
        Runnable task = (Runnable) key.attachment();
        if(task != null) task.run();
    }

    protected Selector getSelector(){
        return selector;
    }
}
