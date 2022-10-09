package org.hzz.chapter03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyFile {
    public static void main(String[] args) throws IOException {
        Path source = Paths.get("tmp\\chapter03\\b.txt");
        Path target = Paths.get("tmp\\chapter03\\b-copy.txt");
        // CopyOption see: https://docs.oracle.com/javase/tutorial/essential/io/copy.html
        Files.copy(source,target, StandardCopyOption.REPLACE_EXISTING);
    }
}
