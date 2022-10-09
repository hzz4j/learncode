package org.hzz.chapter07;

import java.io.IOException;
import java.nio.file.*;

public class ListDirectoryContents {
    public static void main(String[] args) {
        Path dir = Paths.get("tmp\\chapter07");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file: stream) {
                System.out.println(file.getFileName());
            }
        } catch (IOException | DirectoryIteratorException x) {
            // IOException can never be thrown by the iteration.
            // In this snippet, it can only be thrown by newDirectoryStream.
            System.err.println(x);
        }
    }
}
/**
 * a.txt
 * one
 * tmp3383471967397502179
 */