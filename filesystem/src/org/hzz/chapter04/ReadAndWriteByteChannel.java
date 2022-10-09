package org.hzz.chapter04;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ReadAndWriteByteChannel {
    public static void main(String[] args) throws IOException {
        Path readPath = Paths.get("tmp\\chapter04\\small.txt");
        Path writePath = Paths.get("tmp\\chapter04\\small-channel.txt");

        try(SeekableByteChannel inChannel = Files.newByteChannel(readPath);
            SeekableByteChannel outChannel = Files.newByteChannel(writePath, StandardOpenOption.CREATE,StandardOpenOption.WRITE)){
            final int BUFFER_CAPACITY = 10;
            ByteBuffer byteBuffer = ByteBuffer.allocate(BUFFER_CAPACITY);

            while(inChannel.read(byteBuffer) > 0){
                // change to read model
                byteBuffer.flip();
                outChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        }
    }
}
