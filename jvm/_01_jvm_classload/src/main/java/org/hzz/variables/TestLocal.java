package org.hzz.variables;

public class TestLocal {

    {
        int a;
        final int b = 1;
    }

    static{
        int a;
        final int b = 1;
    }

    TestLocal(){
        int a;
        final int b = 1;
    }

    private void test(){
        int c = 2;
        {
            int a;
            final int b = 1;
            // 可以访问c
            System.out.println(c);
        }
        // 无法访问b
        //System.out.println(b);
        int a;
        final int b = 1;
    }
}
