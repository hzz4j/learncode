package org.hzz.adapter.v2;

import org.hzz.adapter.v2.Adaptee;
import org.hzz.adapter.v2.Target;

public class Adapter extends Adaptee implements Target {
    @Override
    public int output5v() {
        int i = output220v();
        // do something else ...
        System.out.println(String.format( "原始电压： %d v  - >  输出电压： %d  v  ",i,5 ));
        return 5;
    }
}
