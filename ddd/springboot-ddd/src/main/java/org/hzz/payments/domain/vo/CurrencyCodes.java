package org.hzz.payments.domain.vo;

public enum CurrencyCodes {
    CNY("CNY", "人民币"),
    USD("USD", "美元"),
    EUR("EUR", "欧元"),
    JPY("JPY", "日元"),
    GBP("GBP", "英镑"),
    HKD("HKD", "港币");

    private final String code;
    private final String name;
    private CurrencyCodes(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
