package org.hzz.adpater.v1;

public class Main {
    public static void main(String[] args) {
        Adapee adapee = new Adapee();
        Adaptor adaptor = new Adaptor(adapee);
        adaptor.output5V();
    }
}
