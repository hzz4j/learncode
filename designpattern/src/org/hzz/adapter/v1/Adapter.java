package org.hzz.adapter.v1;

public class Adapter implements Target{
    private Adaptee adaptee;

    public Adapter(Adaptee adaptee){
        this.adaptee = adaptee;
    }
    @Override
    public int output5v() {
        int i = adaptee.output220v();
        // do something else ...
        System.out.println(String.format( "原始电压： %d v  - >  输出电压： %d  v  ",i,5 ));
        return 5;
    }
}
