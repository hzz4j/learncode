package org.hzz.chapter04;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Arrays;

public class WriteSmallFile {
    private static final String MSG = String.join("\n",
            "静默 Learning Java NIO File",
            "与超过 800 万 开发者一起发现、参与优秀开源项目，私有仓库也完全免费 ：）");
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("tmp\\chapter04\\small.txt");
        Files.write(path,MSG.getBytes(StandardCharsets.UTF_8));

        String[] lines = MSG.split("\\n");
        Files.write(path,Arrays.asList(lines), StandardOpenOption.APPEND);
    }
}
