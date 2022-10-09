package org.hzz.chapter03;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class BasicAttrFile {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("imgs\\avatar.jpg");
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
        System.out.println("creationTime: " + attr.creationTime());
        System.out.println("lastAccessTime: " + attr.lastAccessTime());
        System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

        System.out.println("isDirectory: " + attr.isDirectory());
        System.out.println("isOther: " + attr.isOther());
        System.out.println("isRegularFile: " + attr.isRegularFile());
        System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
        System.out.println("size: " + attr.size());
    }
}
/**
 * creationTime: 2022-10-09T07:29:07.22373Z
 * lastAccessTime: 2022-10-09T07:29:07.22373Z
 * lastModifiedTime: 2022-09-21T17:23:44.518Z
 * isDirectory: false
 * isOther: false
 * isRegularFile: true
 * isSymbolicLink: false
 * size: 39364
 */