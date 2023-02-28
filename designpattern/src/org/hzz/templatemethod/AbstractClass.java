package org.hzz.templatemethod;

public abstract class AbstractClass {
    public void operation(){
        // open
        System.out.println(" pre ... ");
        System.out.println(" step1 ");
        System.out.println(" step2 ");
        templateMethod();
        // ....
    }

    public abstract void templateMethod();
}
