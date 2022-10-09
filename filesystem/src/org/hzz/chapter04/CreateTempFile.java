package org.hzz.chapter04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateTempFile {
    public static void main(String[] args) throws IOException {
        Path tempFile = Files.createTempFile(null, ".myapp");
        System.out.format("The temporary file" +
                " has been created: %s%n", tempFile);

        Path path = Paths.get("tmp\\chapter04");
        Path tempFile2 = Files.createTempFile(path,"test", ".myapp");
        System.out.format("The temporary file" +
                " has been created: %s%n", tempFile2);
    }
}
/**
 * The temporary file has been created: C:\Users\11930\AppData\Local\Temp\2585945490360134177.myapp
 * The temporary file has been created: tmp\chapter04\test831222729464056259.myapp
 */