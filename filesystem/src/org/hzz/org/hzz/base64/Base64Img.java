package org.hzz.org.hzz.base64;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

public class Base64Img {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("imgs/avatar.JPG");
        FileOutputStream out = null;
        try{
            out = new FileOutputStream("imgs/avatar.txt");
            byte[] bytes = Files.readAllBytes(path);
            byte[] encode = Base64.getEncoder().encode(bytes);
            // 加前缀方便在浏览器中显示
            out.write("data:image/png;base64,".getBytes());
            out.write(encode);
        }finally {
            if(out != null){
                out.close();
            }
        }
    }
}
