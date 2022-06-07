package org.hzz.adpater.v2;

public class Adaptor extends org.hzz.adpater.v2.Adapee implements Target {
    private final int v = 5;

    @Override
    public int output5V() {
        int val = output220V();
        System.out.printf("将 %dV ------ 转化为 -----> %dV",val,v);
        return v;
    }
}
