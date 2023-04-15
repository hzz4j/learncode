package org.hzz;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class NettyByteBuf2 {
    public static void main(String[] args) {
        ByteBuf bytebuf = Unpooled.copiedBuffer("Hello World", Charset.forName("utf-8"));
        System.out.println(ByteBufUtil.prettyHexDump(bytebuf));

        if(bytebuf.hasArray()){
            byte[] content = bytebuf.array();
            //将content转成字符串
            System.out.println(new String(content, Charset.forName("utf-8")));
            System.out.println(ByteBufUtil.prettyHexDump(bytebuf));

            System.out.println("readable bytes = "+bytebuf.readableBytes());

            // 范围读取
            System.out.println(bytebuf.getCharSequence(0,5,Charset.forName("utf-8")));
            System.out.println(bytebuf.getCharSequence(6,5,Charset.forName("utf-8")));
            System.out.println(ByteBufUtil.prettyHexDump(bytebuf));
        }
    }
}
/**
 *          +-------------------------------------------------+
 *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
 * +--------+-------------------------------------------------+----------------+
 * |00000000| 48 65 6c 6c 6f 20 57 6f 72 6c 64                |Hello World     |
 * +--------+-------------------------------------------------+----------------+
 * Hello World
 *          +-------------------------------------------------+
 *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
 * +--------+-------------------------------------------------+----------------+
 * |00000000| 48 65 6c 6c 6f 20 57 6f 72 6c 64                |Hello World     |
 * +--------+-------------------------------------------------+----------------+
 * readable bytes = 11
 * Hello
 * World
 *          +-------------------------------------------------+
 *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
 * +--------+-------------------------------------------------+----------------+
 * |00000000| 48 65 6c 6c 6f 20 57 6f 72 6c 64                |Hello World     |
 * +--------+-------------------------------------------------+----------------+
 */