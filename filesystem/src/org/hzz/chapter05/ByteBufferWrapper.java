package org.hzz.chapter05;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteBufferWrapper {
    public static void main(String[] args) {
        ByteBuffer defaultBuffer = ByteBuffer.allocate(11);
        printPositionAndLimit(defaultBuffer);  // position = 0; limit = 11

        ByteBuffer buffer = ByteBuffer.wrap("Hello World".getBytes(StandardCharsets.UTF_8));
        // wrap byte array
        printPositionAndLimit(buffer);  // position = 0; limit = 11
        buffer.position(3);  // 手动改变position
        printPositionAndLimit(buffer);  // position = 3; limit = 11
        // array并没有改变position,和position没有关系，只是复制值。
        System.out.println(Arrays.toString(buffer.array()));
        printPositionAndLimit(buffer);  // position = 0; limit = 11

    }

    public static void printPositionAndLimit(ByteBuffer buffer){
        System.out.format("position = %s; limit = %s%n",buffer.position(),buffer.limit());
    }
}
/**
 * position = 0; limit = 11
 * position = 0; limit = 11
 * position = 3; limit = 11
 * [72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100]
 * position = 3; limit = 11
 */