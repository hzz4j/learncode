package org.hzz.chapter03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MoveFile {
    public static void main(String[] args) throws IOException {
        Path source = Paths.get("tmp\\chapter03\\b-copy.txt");
        Path target = Paths.get("tmp\\chapter03-2\\b-copy.txt");
        Files.move(source,target);
    }
}
