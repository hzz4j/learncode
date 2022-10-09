package org.hzz.chapter02;

import java.nio.file.Path;
import java.nio.file.Paths;

public class IteratorPathName {
    public static void main(String[] args) {
        Path path = Paths.get("D:\\learncode\\filesystem\\logs\\foo.log");
        // The first element returned is that closest to the root in the directory tree.
        for (Path name: path){
            System.out.println(name);
        }
    }
}
/**
 * learncode
 * filesystem
 * logs
 * foo.log
 */
