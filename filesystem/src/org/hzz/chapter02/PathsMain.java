package org.hzz.chapter02;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathsMain {
    public static void main(String[] args) {
        String currentPathStr = System.getProperty("user.dir");
        System.out.println(currentPathStr);
        // create path  尽管这个路劲不存在，但是也不会报错
        Path logsPath = Paths.get(currentPathStr, "logs","foo.log");
        System.out.format("toString: %s%n", logsPath.toString());
        System.out.format("getFileName: %s%n", logsPath.getFileName());
        System.out.format("getName(0): %s%n", logsPath.getName(0));
        System.out.format("getNameCount: %d%n", logsPath.getNameCount());
        System.out.format("subpath(0,2): %s%n", logsPath.subpath(0,2));
        System.out.format("getParent: %s%n", logsPath.getParent());
        System.out.format("getRoot: %s%n", logsPath.getRoot());
        System.out.format("toUri: %s%n", logsPath.toUri());
    }
}
/**
 * D:\learncode\filesystem
 * toString: D:\learncode\filesystem\logs\foo.log
 * getFileName: foo.log
 * getName(0): learncode
 * getNameCount: 4
 * subpath(0,2): learncode\filesystem
 * getParent: D:\learncode\filesystem\logs
 * getRoot: D:\
 * toUri: file:///D:/learncode/filesystem/logs/foo.log
 */