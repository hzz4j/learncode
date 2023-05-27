package org.hzz.test;

public class PaymentMethodTest {
    public enum PaymentMethod {
        CREDIT_CARD("信用卡"),
        ALIPAY("支付宝"),
        WECHATPAY("微信支付");
        private String name;
        PaymentMethod(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        PaymentMethod creditCard = PaymentMethod.valueOf("CREDIT_CARD");

        System.out.println(creditCard.name);
        // 非法参数：No enum constant org.hzz.test.PaymentMethodTest.PaymentMethod.支付宝
        //PaymentMethod 支付宝 = PaymentMethod.valueOf("支付宝");
        //System.out.println(支付宝.name);
    }
}
