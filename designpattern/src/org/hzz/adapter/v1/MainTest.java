package org.hzz.adapter.v1;

public class MainTest {
    public static void main(String[] args) {
        Target target = new Adapter(new Adaptee());
        target.output5v();
    }
}
/**
 * 原始电压： 220 v  - >  输出电压： 5  v
 */