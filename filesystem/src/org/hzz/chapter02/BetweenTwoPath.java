package org.hzz.chapter02;

import java.nio.file.Path;
import java.nio.file.Paths;

public class BetweenTwoPath {
    public static void main(String[] args) {
        Path p1 = Paths.get("home");
        Path p3 = Paths.get("home/sally/bar");

        // 相对路径
        Path p1_to_p3 = p1.relativize(p3);  // sally\bar
        Path p3_to_p1 = p3.relativize(p1);  // ..\..
        Path p3_to_p3 = p3.relativize(p3);  // 为空

        System.out.format("%s%n %s%n %s%n",p1_to_p3,p3_to_p1,p3_to_p3);
    }
}
