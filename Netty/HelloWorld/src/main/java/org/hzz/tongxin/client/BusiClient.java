package org.hzz.tongxin.client;

import org.hzz.busivo.User;
import org.hzz.busivo.UserContact;

import java.util.Scanner;

/**
 * 很好的交互式操作
 */
public class BusiClient {
    public static void main(String[] args) throws Exception {
        NettyClient nettyClient = new NettyClient();
        new Thread(nettyClient).start();
        while(!nettyClient.isConnected()){
            synchronized (nettyClient){
                nettyClient.wait();
            }
        }
        System.out.println("网络通信已准备好，可以进行业务操作了........");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String msg = scanner.next();
            if (msg == null) {
                break;
            } else if ("q".equals(msg.toLowerCase())) {
                nettyClient.close();
                scanner.close();
                while(nettyClient.isConnected()){
                    synchronized (nettyClient){
                        System.out.println("等待网络关闭完成....");
                        nettyClient.wait();
                    }
                }
                System.exit(1);
            } else if("v".equals(msg.toLowerCase())){
                User user = new User();
                user.setAge(19);
                String userName = "mark";
                user.setUserName(userName);
                user.setId("No:1");
                user.setUserContact(
                        new UserContact(userName+"@tuling.com",
                                "133"));
                nettyClient.send(user);
            }else if("o".equals(msg.toLowerCase())){
                User user = new User();
                user.setAge(23);
                String userName = "oneway";
                user.setUserName(userName);
                user.setId("No:1");
                user.setUserContact(
                        new UserContact(userName+"@hzz.org",
                                "331"));
                nettyClient.sendOneWay(user);
            }
            else {
                nettyClient.send(msg);
            }
        }
    }
}
