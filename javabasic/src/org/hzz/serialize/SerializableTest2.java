package org.hzz.serialize;

import java.io.*;

public class SerializableTest2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 创建输入流（从磁盘反序列化）
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("test.out"));
        // 反序列化
        Mobile mobile = (Mobile) ois.readObject();
        ois.close();
        System.out.println(mobile);
    }
}
/**
 * Mobile(name=iPhone, price=null)
 */