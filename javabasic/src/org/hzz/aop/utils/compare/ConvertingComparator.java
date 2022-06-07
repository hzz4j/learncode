package org.hzz.aop.utils.compare;

import org.hzz.aop.utils.compare.Converter;

import java.util.Comparator;

public class ConvertingComparator<S, T> implements Comparator<S> {
    private Comparator<T> comparator;
    private Converter<S,T> converter;
    public ConvertingComparator(Comparator<T> comparator, Converter<S, T> converter){
        this.comparator = comparator;
        this.converter = converter;
    }
    @Override
    public int compare(S o1, S o2) {
        T t1 = converter.convert(o1);
        T t2 = converter.convert(o2);
        return comparator.compare(t1,t2);
    }
}
