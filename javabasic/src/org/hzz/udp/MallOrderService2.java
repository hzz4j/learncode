package org.hzz.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class MallOrderService2 {
    public static void main(String[] args) {
        try {
            DatagramSocket udpSocket = new DatagramSocket(Constants.PORT);
            byte[] data = new byte[1<<10]; // 1kb
            DatagramPacket packet = new DatagramPacket(data, data.length);
            while(true){
                udpSocket.receive(packet);
                int length = packet.getLength();
                String s = new String(data).trim();
                System.out.println("来自："+packet.getSocketAddress());
                System.out.println("实际: ----------------------------length = "+length);
                System.out.println(s);
                System.out.println(new String(data,0,length));
            }


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/**
 * 来自：/127.0.0.1:63214
 */