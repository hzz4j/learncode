package org.hzz.chapter03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MoveDir {
    public static void main(String[] args) throws IOException {
        Path source = Paths.get("tmp\\chapter03");
        Path target = Paths.get("tmp\\chapter03-2\\chapter03");

        Files.move(source,target);
    }
}
