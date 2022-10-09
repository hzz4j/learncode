package org.hzz.chapter04;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WriteTextFile {
    private static final String MSG = String.join("\n",
            "静默 Learning Java NIO File",
            "与超过 800 万 开发者一起发现、参与优秀开源项目，私有仓库也完全免费 ：）");

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("tmp\\chapter04\\small.txt");
        try(BufferedWriter writer = Files.newBufferedWriter(path)){
            writer.write(MSG);
        }
    }
}
