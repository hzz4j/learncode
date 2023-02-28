package org.hzz.builder.v2;

public class MainTest {
    public static void main(String[] args) {
        Product product = new Product.Builder()
                .builderPart1("part1")
                .builderPart2("part2")
                .builderPart3("part3")
                .builderPart4("part4")
                .build();

        System.out.println(product);
    }
}
