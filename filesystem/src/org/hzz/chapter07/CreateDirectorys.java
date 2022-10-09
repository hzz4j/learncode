package org.hzz.chapter07;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateDirectorys {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("tmp\\chapter07\\one\\two\\three");
        Files.createDirectories(path);
    }
}
