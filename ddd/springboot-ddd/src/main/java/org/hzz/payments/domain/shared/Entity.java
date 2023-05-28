package org.hzz.payments.domain.shared;

/**
 * DDD中的实体
 */
public interface Entity<E,ID> {
    /**
     * 实体按id进行比较，而不是按属性进行比较。
     * @param other 另外一个实体
     * @return 如果两个实体的id相同，则返回<code>true<code/>，否则返回<code>false<code/>
     */
    boolean sameIdentityAs(E other);
    ID identity();
}
