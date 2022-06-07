package org.hzz.aop.utils.compare;

public interface Converter<S, T> {
    T convert(S source);
}
