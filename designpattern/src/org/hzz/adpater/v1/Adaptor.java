package org.hzz.adpater.v1;

public class Adaptor implements Target{
    private final int v = 5;
    private Adapee adapee;

    public Adaptor(Adapee adapee){
        this.adapee = adapee;
    }

    @Override
    public int output5V() {
        int val = adapee.output220V();
        System.out.printf("将 %dV ------ 转化为 -----> %dV",val,v);
        return v;
    }
}
