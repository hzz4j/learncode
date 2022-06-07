package org.hzz.lambd;

import org.hzz.enm.value.Main;

import java.util.*;
import java.util.concurrent.*;

public class MainTest implements Runnable {

    private Map<String,Client> map = new ConcurrentHashMap<>();
    private ScheduledExecutorService executorService;

    public MainTest(){
        executorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
//                thread.setDaemon(true);
                thread.setName("com.alibaba.nacos.client.test");
                return thread;
            }
        });
//        executorService.execute(this);
        this.executorService.schedule(this,1000, TimeUnit.MILLISECONDS);
    }

    private DataSource dataSource = new DataSource() {
        @Override
        public List<String> getData(Client client) {
            return doSrvIpxt(client.toString(),client.getIp(),client.getPort());
        }
    };

    public static void main(String[] args) {
        List<String> res1 = new MainTest().doSrvIpxt("hello", "192.168.187.135", 8970);
        System.out.println(res1);
        System.out.println("--------------------------main----------------------------------");
    }

    private List<String> doSrvIpxt(String name,String ip,int port){
        if(!map.containsKey(buildKey(ip,port))){
            map.putIfAbsent(buildKey(ip,port),new Client(ip,port,dataSource));
        }


        return Arrays.<String>asList("Java","Nacos","RocketMQ",name);
    }

    private String buildKey(String ip,int port){
        return ip+"@@"+port;
    }


    @Override
    public void run() {
        System.out.println("------------------------"+Thread.currentThread().getName()+"--------------------");
        System.out.println("size:"+map.size());
        try{
            for (Map.Entry<String, Client> entry:
                    map.entrySet()) {
                Client client = entry.getValue();
                DataSource dataSource = client.getDataSource();
                List<String> data = dataSource.getData(client);
                map.remove(entry.getKey());
                System.out.println(data);
            }
        }catch (Throwable e){
            System.out.println(e);
        }
        this.executorService.schedule(this,1000, TimeUnit.MILLISECONDS);

    }
}
