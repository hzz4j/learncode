package org.hzz.chapter04;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Methods for Unbuffered Streams and Interoperable with java.io APIs
 * https://docs.oracle.com/javase/tutorial/essential/io/file.html#streams
 */
public class ReadAndWriteStreamFile {
    public static void main(String[] args) {
        Path readPath = Paths.get("tmp\\chapter04\\avatar.jpg");
        Path writePath = Paths.get("tmp\\chapter04\\avatar-copy.jpg");

        try(InputStream in = Files.newInputStream(readPath);
            OutputStream out = Files.newOutputStream(writePath)){

            BufferedInputStream bin = new BufferedInputStream(in);
            BufferedOutputStream bout = new BufferedOutputStream(out);
            byte[] contents = new byte[1024];
            int length = 0;
            while((length = bin.read(contents)) != -1){
                bout.write(contents,0,length);
            }
            // To flush a stream manually, invoke its flush method.
            // The flush method is valid on any output stream, but has no effect unless the stream is buffered.
            bout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
