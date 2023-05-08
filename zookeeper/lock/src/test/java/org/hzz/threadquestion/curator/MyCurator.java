package org.hzz.threadquestion.curator;

import org.apache.curator.framework.listen.Listenable;
import org.apache.curator.framework.state.ConnectionStateListener;

import java.util.List;

public class MyCurator {
    private MyConnectionStateManager connectionStateManager;

    public MyCurator(){
        connectionStateManager = new MyConnectionStateManager();
    }
    public void start(){
        connectionStateManager.start();
    }

    public List<ConnectionStateListener> getConnectionStateListenable()
    {
        return connectionStateManager.getListenable();
    }
}
