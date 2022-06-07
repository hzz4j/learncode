package org.hzz;

import java.io.*;

public class MyClassLoader extends ClassLoader{

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        ClassLoader extClassLoader = getSystemClassLoader().getParent();
        Class<?> aClass = null;
        try{
             aClass = extClassLoader.loadClass(name);
        }catch (ClassNotFoundException ignore){}

        if(aClass != null){
            return aClass;
        }
        return findClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = getBytes("D:\\learncode\\classloader\\out\\production\\classloader\\org\\hzz\\Test1.class");
        // defineClass方法中会进行类的沙箱安全保护机制
        Class<?> aClass = this.defineClass(name, data, 0, data.length);
        return aClass;
    }

    private byte[] getBytes(String path){
        byte[] data = null;
        FileInputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            in = new FileInputStream(new File(path));
            int b;
            while((b = in.read())!=-1){
                out.write(b);
            }
            data = out.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(in != null) in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }


    public static void main(String[] args) throws ClassNotFoundException {
        MyClassLoader myClassLoader1 = new MyClassLoader();
        MyClassLoader myClassLoader2 = new MyClassLoader();
        Class<?> aClass = myClassLoader1.loadClass("org.hzz.Test1");
        Class<?> bClass = myClassLoader2.loadClass("org.hzz.Test1");
        System.out.println(aClass == bClass); // false
        System.out.println(aClass.getClassLoader()); // org.hzz.MyClassLoader@1b6d3586
    }
}
