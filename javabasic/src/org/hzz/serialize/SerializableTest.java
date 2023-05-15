package org.hzz.serialize;

import java.io.*;

public class SerializableTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Mobile mobile = new Mobile("iPhone");

        System.out.println(mobile);
        // 创建输出流（序列化内容到磁盘）
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("test.out"));
        // 序列化对象
        oos.writeObject(mobile);
        oos.flush();
        oos.close();

        // 创建输入流（从磁盘反序列化）
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("test.out"));
        // 反序列化
        Mobile mobile1 = (Mobile) ois.readObject();
        ois.close();
        System.out.println(mobile);
    }
}
/**
 * Mobile(name=iPhone)
 * Mobile(name=iPhone)
 */