package org.hzz.chapter03;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStoreAttr {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("imgs\\avatar.jpg");
        FileStore store = Files.getFileStore(path);
        long total = store.getTotalSpace() / 1024 / 1024 / 1024;
        long used = (store.getTotalSpace() -
                store.getUnallocatedSpace()) / 1024 / 1024 / 1024;
        long avail = store.getUsableSpace() / 1024 / 1024 / 1024;

        // D:\learncode\filesystem\imgs\avatar.jpg
        // 实际上打印的是这个文件所在的磁盘的空间
        // total = 789G ; used = 781G ; avail = 8G
        System.out.printf("total = %dG ; used = %dG ; avail = %dG%n",total,used,avail);
    }
}
