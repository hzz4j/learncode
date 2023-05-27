package org.hzz.payments.domain.shared;

/**
 * 值对象
 */
public interface ValueObject<T> {
    /**
     *  值对象通过其属性的值进行比较，它们没有标识
     * @param other 另外一个值对象
     * @return 如果两个值对象的属性值完全相同，则返回<code>true</code>
     */
    boolean sameValueAs(T other);
}

