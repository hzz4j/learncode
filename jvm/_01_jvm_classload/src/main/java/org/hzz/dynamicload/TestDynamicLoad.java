package org.hzz.dynamicload;

public class TestDynamicLoad {
    public static A a = new A();

    static {
        System.out.println("*************load TestDynamicLoad*******************");
    }

    public D d = new D();

    public static void main(String[] args) {
        System.out.println("*************main method****************************");
        B b = new B();
        C c = null;
    }
}

class A {
    static {
        System.out.println("*************load A*********************************");
    }
}

class B {
    static {
        System.out.println("*************load B*********************************");
    }
}

class C {
    static {
        System.out.println("*************load C*********************************");
    }
}

class D {
    static {
        System.out.println("*************load D*********************************");
    }
}
/**
 * *************load A*********************************
 * *************load TestDynamicLoad*******************
 * *************main method****************************
 * *************load B*********************************
 */
