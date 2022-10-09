package org.hzz.chapter03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CheckingFile {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("imgs/avatar.jpg");
        Path path2 = Paths.get("imgs/avatar.jpg");
        System.out.format("file is exist? %s%n",Files.exists(path, LinkOption.NOFOLLOW_LINKS));
        System.out.format("Is the same file? %s%n",Files.isSameFile(path,path2));
    }
}
