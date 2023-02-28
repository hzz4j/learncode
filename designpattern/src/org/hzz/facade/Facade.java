package org.hzz.facade;

import org.hzz.facade.system.SubSystem1;
import org.hzz.facade.system.SubSystem2;
import org.hzz.facade.system.SubSystem3;

public class Facade {
    SubSystem1 subSystem1 = new SubSystem1();
    SubSystem2 subSystem2 = new SubSystem2();
    SubSystem3 subSystem3 = new SubSystem3();

    public void doSomething(){
        subSystem1.method();
        subSystem2.method();
        subSystem3.method();
    }
}
