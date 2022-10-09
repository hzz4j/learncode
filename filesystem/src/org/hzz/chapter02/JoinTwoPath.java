package org.hzz.chapter02;

import java.nio.file.Path;
import java.nio.file.Paths;

public class JoinTwoPath {
    public static void main(String[] args) {
        Path path = Paths.get("D:\\learncode\\filesystem");
        // D:\learncode\filesystem\logs\foo.log
        System.out.format("%s %n",path.resolve("logs/foo.log"));
    }
}
