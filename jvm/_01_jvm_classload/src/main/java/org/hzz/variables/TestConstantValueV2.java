package org.hzz.variables;

import java.lang.reflect.Field;

public class TestConstantValueV2 {
    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Class<?> v2Class = ClassLoader.getSystemClassLoader().loadClass("org.hzz.variables.V2");
        Field a = v2Class.getField("A");
        System.out.println(a.get(null));
    }
}

/**
 * init success
 * 1
 */
class V2{
    public final static int A = 1;
    static {
        System.out.println("init success");
    }
}
