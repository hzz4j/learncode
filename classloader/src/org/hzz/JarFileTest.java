package org.hzz;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class JarFileTest extends ClassLoader{
    public static void main(String[] args) throws Exception {
        JarFile jarFile = new JarFile("D:\\learncode\\classloader\\myjars\\hzz-test.jar");
        ZipEntry entry = jarFile.getEntry("org/hzz/Test1.class");
        // 处理二进制数据
        int contentLength = (int)entry.getSize();
        byte[] binaryContent = new byte[contentLength];
        InputStream binaryStream = jarFile.getInputStream(entry);
        int pos = 0;
        while (true) {
            int n = binaryStream.read(binaryContent, pos,
                    binaryContent.length - pos);
            if (n <= 0)
                break;
            pos += n;
        }
        // 加载类
        Class<?> aClass = new JarFileTest().defineClass("org.hzz.Test1",
                binaryContent, 0, binaryContent.length);
        Method main = aClass.getDeclaredMethod("main", String[].class);
        //Thread.currentThread().setContextClassLoader(aClass.getClassLoader());
        main.invoke(null, (Object)new String[]{});
        System.out.println(aClass.getClassLoader());
//        Enumeration entries = jarFile.entries();
//        while (entries.hasMoreElements()) {
//            System.out.println(entries.nextElement());
//        }
    }
}
