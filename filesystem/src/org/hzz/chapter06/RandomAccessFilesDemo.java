package org.hzz.chapter06;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class RandomAccessFilesDemo {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("tmp\\chapter06\\data.txt");
        String msg = "I was here!\n";

        ByteBuffer out = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
        ByteBuffer copy = ByteBuffer.allocate(12);

        //RandomAccessFile file = new RandomAccessFile("tmp\\chapter06\\data.txt", "rw");
        //FileChannel channel = file.getChannel();

        try(FileChannel channel = FileChannel.open(path, StandardOpenOption.READ,
                                StandardOpenOption.WRITE)){
            // read the first 12 byte of the file
            int nread = 0;
            do {
                nread = channel.read(copy);
            }while (nread != -1 && copy.hasRemaining());


            // write "I was here\n" at the beginning of file
            channel.position(0);
            while(out.hasRemaining())
                channel.write(out);

            out.rewind();
            // move to the end of the file .Copy 12 bytes to
            // the end of the file.then write "I was here!\n" again

            channel.position(channel.size()-1);
            copy.flip();
            while(copy.hasRemaining())
                channel.write(copy);
            while(out.hasRemaining())
                channel.write(out);
        }
    }
}
