package org.hzz.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class NacosServer {
    public static void main(String[] args) {
        InetSocketAddress socketAddr = new InetSocketAddress("127.0.0.1", Constants.PORT);
        try{
            // 不传入端口，让系统自己选一个端口
            DatagramSocket udpSocket = new DatagramSocket();
            System.out.println("启动在端口："+udpSocket.getLocalPort());
            byte[] dataBytes = "Hello World".getBytes();
            DatagramPacket packet = new DatagramPacket(dataBytes, dataBytes.length, socketAddr);
            udpSocket.send(packet);
        }catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
/**
 * 启动在端口：63214
 */