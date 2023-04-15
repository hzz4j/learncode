package org.hzz;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

public class NettyByteBuf {
    static String msg = "hello";
    public static void main(String[] args) {
        // 创建byteBuf对象，该对象内部包含一个字节数组byte[10]
        // 通过readerindex和writerIndex和capacity，将buffer分成三个区域
        // 已经读取的区域：[0,readerindex)
        // 可读取的区域：[readerindex,writerIndex)
        // 可写的区域: [writerIndex,capacity)
        ByteBuf buffer = Unpooled.buffer(10);
        System.out.println(ByteBufUtil.prettyHexDump(buffer));
        for (int i = 0; i < msg.length(); i++) {
            buffer.writeByte(msg.charAt(i));
        }
        System.out.println(ByteBufUtil.prettyHexDump(buffer));
        System.out.println("------------------");
        for(int i = 0;i<msg.length();i++){
            System.out.println((char)buffer.getByte(i));
        }
        System.out.println(ByteBufUtil.prettyHexDump(buffer));
        System.out.println("------------------");

        for(int i = 0;i<msg.length();i++){
            System.out.println((char)buffer.readByte());
        }
        System.out.println(ByteBufUtil.prettyHexDump(buffer));
        System.out.println("------------------");
    }
}
/**
 *
 +-------------------------------------------------+
 |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
 +--------+-------------------------------------------------+----------------+
 |00000000| 68 65 6c 6c 6f                                  |hello           |
 +--------+-------------------------------------------------+----------------+
 ------------------
 h
 e
 l
 l
 o
 +-------------------------------------------------+
 |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
 +--------+-------------------------------------------------+----------------+
 |00000000| 68 65 6c 6c 6f                                  |hello           |
 +--------+-------------------------------------------------+----------------+
 ------------------
 h
 e
 l
 l
 o

 ------------------

 */