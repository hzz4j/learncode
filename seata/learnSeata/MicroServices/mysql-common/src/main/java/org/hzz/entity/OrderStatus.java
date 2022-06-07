package org.hzz.entity;

public enum OrderStatus {
    /**
     * INIT
     */
    INIT(0),
    /**
     * SUCCESS
     */
    SUCCESS(1),
    /**
     * FAIL
     */
    FAIL(-1);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
