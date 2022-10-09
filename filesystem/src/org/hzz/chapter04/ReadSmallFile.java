package org.hzz.chapter04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReadSmallFile {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("tmp\\chapter04\\small.txt");

        byte[] bytes = Files.readAllBytes(path);
        System.out.println(new String(bytes));
        System.out.println("-------------------------------");
        List<String> lines = Files.readAllLines(path);
        lines.forEach(line -> System.out.println(line));
    }
}
/**output
 * 静默 Learning Java NIO File
 * 与超过 800 万 开发者一起发现、参与优秀开源项目，私有仓库也完全免费 ：）
 * -------------------------------
 * 静默 Learning Java NIO File
 * 与超过 800 万 开发者一起发现、参与优秀开源项目，私有仓库也完全免费 ：）
 */
