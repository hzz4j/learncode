package org.hzz.interviews;

public class FinalReferenceTest {
    public static void main(String[] args) {
        final int[] iarr = {1,2,3};
        iarr[0] = 0; // 合法
        //iarr = null; // 非法

        final Person p = new Person(10);
        p.setAge(20); // 合法
        //p = null; // 非法
    }
}
/**
 * 非法： 编译会报错，如java: 无法为最终变量p分配值
 */