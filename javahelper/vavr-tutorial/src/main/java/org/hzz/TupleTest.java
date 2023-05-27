package org.hzz;

import io.vavr.Tuple3;

public class TupleTest {
    public static void main(String[] args) {
        Tuple3<String,Integer,String> tuple3 = new Tuple3<>("Q10Viking",8,"java8");
        String name = tuple3._1();
        Integer version = tuple3._2();
        String language = tuple3._3();

        System.out.printf("name:%s is learning language:%s of version:%d\n",name,language,version);
    }
    // name:Q10Viking is learning language:java8 of version:8
}
