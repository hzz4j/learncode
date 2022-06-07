package org.hzz;

import java.io.*;

public class MyClassLoader1 extends ClassLoader{

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
        if(name.equals("org.hzz.A"))
            data = getBytes("D:\\learncode\\classloader\\out\\production\\classloader\\org\\hzz\\A.class");
        if(name.equals("org.hzz.B"))
            data = getBytes("D:\\learncode\\classloader\\out\\production\\classloader\\org\\hzz\\B.class");
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


    public static void main(String[] args) throws Exception {
        MyClassLoader1 myClassLoader1 = new MyClassLoader1();
        Class<?> aClass = myClassLoader1.loadClass("org.hzz.Test1");
        Object o = aClass.newInstance();
        System.out.println(Test1.class.getClassLoader()); // 可以看到AppClassLoader是由类加载器加载的
        Test1 t1 = (Test1) o;  // java.lang.ClassCastException: org.hzz.Test1 cannot be cast to org.hzz.Test1
        ((Test1) o).methodB();
    }
}
