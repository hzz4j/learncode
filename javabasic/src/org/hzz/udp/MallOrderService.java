package org.hzz.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class MallOrderService {
    public static void main(String[] args) {
        try {
            DatagramSocket udpSocket = new DatagramSocket(Constants.PORT);
            byte[] data = new byte[1<<10]; // 1kb
            DatagramPacket packet = new DatagramPacket(data, data.length);
            int count = 0;
            while(true){
                udpSocket.receive(packet);
                int length = packet.getLength();

                System.out.println(count++ + ": ---------------------------------");
                System.out.println(new String(data,0,length));
            }


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
