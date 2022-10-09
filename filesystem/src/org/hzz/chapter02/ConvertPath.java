package org.hzz.chapter02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConvertPath {
    public static void main(String[] args) throws IOException {

        // 设置相对路径，等价于 Paths.get("logs\\foo.log")
        Path logsPath = Paths.get("logs","foo.log");
        System.out.println(logsPath);
        System.out.println(logsPath.toAbsolutePath());
        System.out.println(logsPath.toUri());

        // 与toAbsolutePath不同的是，如果该路径不存在则会报错NoSuchFileException
        System.out.println(logsPath.toRealPath());
    }
}
/**
 * logs\foo.log
 * D:\learncode\filesystem\logs\foo.log
 * file:///D:/learncode/filesystem/logs/foo.log
 * Exception in thread "main" java.nio.file.NoSuchFileException: D:\learncode\filesystem\logs\foo.log
 */