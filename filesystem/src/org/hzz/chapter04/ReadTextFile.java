package org.hzz.chapter04;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadTextFile {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("tmp\\chapter04\\small.txt");
        try(BufferedReader reader = Files.newBufferedReader(path)){
            String line = null;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        }
    }
}
