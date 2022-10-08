package org.hzz.chapter01;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

public class FileSystemDemo {
    public static void main(String[] args) throws IOException {
        FileSystem fileSystem = FileSystems.getDefault();

        fileSystem.getRootDirectories().forEach(System.out::println);

        for (FileStore store: fileSystem.getFileStores()) {
            long total = store.getTotalSpace() / 1024;
            long used = (store.getTotalSpace() - store.getUnallocatedSpace()) / 1024;
            long avail = store.getUsableSpace() / 1024;
            System.out.format("%-20s %12d %12d %12d%n", store, total, used, avail);
        }
    }
}
/**
 * C:\
 * D:\
 * E:\
 * F:\
 * G:\
 * H:\
 * Windows (C:)            123018236     99776024     23242212
 * DATA (D:)               828327932    819870516      8457416
 * Software (E:)           133943292     97342816     36600476
 * RECOVERY (F:)            14488572     12825860      1662712
 * GRMCULXFRER (G:)        976762452    803494568    173267884
 */