package org.hzz.builder.v1;

public class MainTest {
    public static void main(String[] args) {
        ProductBuilder builder = new DefaultProductBuilder();
        Product product = builder.buildPart1("part1")
                .buildPart2("part2")
                .buildPart3("part3")
                .buildPart4("part4")
                .build();
        System.out.println(product);
    }
}
