package org.hzz.chapter05;

import java.nio.ByteBuffer;

public class ByteBufferFlip {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);

        byteBuffer.put((byte)20);
        byteBuffer.put((byte)'a');
        printPositionAndLimit(byteBuffer);
        byteBuffer.flip(); // 将position重置为了0,limit重置到position的位置
        printPositionAndLimit(byteBuffer);

        int i = 0;
        while(byteBuffer.hasRemaining()){
            byteBuffer.get();
            i++;
        }
        System.out.format("执行了 %d 次 %n",i);
    }

    public static void printPositionAndLimit(ByteBuffer buffer){
        System.out.format("position = %s; limit = %s%n",buffer.position(),buffer.limit());
    }
}
/**
 * position = 2; limit = 4
 * position = 0; limit = 2
 * 执行了 2 次
 */