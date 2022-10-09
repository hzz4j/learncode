package org.hzz.chapter07;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DIrectoryFilter {
    public static void main(String[] args) {
        Path dir = Paths.get("tmp\\chapter07");
        DirectoryStream.Filter<Path> filter = path -> Files.isDirectory(path);

        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir,filter)){
            for (Path path:
                 stream) {
                System.out.println(path.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/**
 * one
 * tmp3383471967397502179
 */