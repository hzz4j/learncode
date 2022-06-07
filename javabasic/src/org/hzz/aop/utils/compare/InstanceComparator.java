package org.hzz.aop.utils.compare;

import java.util.Comparator;

public class InstanceComparator<T> implements Comparator<T> {
    private final Class<?>[] instanceOrder;

    public InstanceComparator(Class<?>... instanceOrder){
        this.instanceOrder = instanceOrder;
    }


    @Override
    public int compare(T o1, T o2) {
        int i1 = getOrder(o1);
        int i2 = getOrder(o2);
        return Integer.compare(i1,i2);
    }


    public int getOrder(T o){
        for(int i=0;i<instanceOrder.length;i++){
            if(instanceOrder[i].isInstance(o)){
                return i;
            }
        }
        return instanceOrder.length;
    }


}
