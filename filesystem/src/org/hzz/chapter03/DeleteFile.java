package org.hzz.chapter03;

import java.io.IOException;
import java.nio.file.*;

public class DeleteFile {
    public static void main(String[] args) {
        Path dir = Paths.get("tmp\\chapter03");
        Path fileA = dir.resolve("a.txt");

        try {
            // 如果文件不为空，会报错 DirectoryNotEmptyException
            Files.delete(dir);
//            Files.delete(fileA);
        }  catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", dir);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", dir);
        } catch (IOException x) {
            // File permission problems are caught here.
            System.err.println(x);
        }
    }
}
