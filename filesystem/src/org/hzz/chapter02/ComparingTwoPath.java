package org.hzz.chapter02;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ComparingTwoPath {
    public static void main(String[] args) {
        Path path = Paths.get("D:\\");
        Path otherPath = Paths.get("D:\\learncode\\filesystem\\logs\\foo.log");
        Path beginPath = Paths.get("C:\\");
        Path endPath = Paths.get("foo.log");

        if(path.equals(otherPath)){
            // equality logic here
            System.out.println("equal");
        }

        if(otherPath.startsWith(beginPath)){
            // path begins with "C:\"
            System.out.println("starts with " + beginPath);
        }

        if(otherPath.endsWith(endPath)){
            // path ends with "foo.log"
            System.out.println("end with " + endPath);
        }
    }
}
