package org.hzz.chapter07;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class PrintDefaultFileSystem {
    public static void main(String[] args) {
        Iterable<Path> rootDirectories = FileSystems.getDefault().getRootDirectories();
        rootDirectories.forEach(System.out::println);
    }
}
/**
 * C:\
 * D:\
 * E:\
 * F:\
 * G:\
 * H:\
 */