package org.hzz.chapter07;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateDirectory {
    public static void main(String[] args){
        Path path = Paths.get("tmp\\chapter07");
        try {
            Files.createDirectory(path);
        }catch (FileAlreadyExistsException e){
            System.out.println(path.toAbsolutePath() + " 已经存在");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
