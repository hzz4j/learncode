package org.hzz.builder.v1;

public interface ProductBuilder {
    ProductBuilder buildPart1(String part1 );
    ProductBuilder buildPart2(String part2 );
    ProductBuilder buildPart3(String part3 );
    ProductBuilder buildPart4(String part4 );
    Product build();
}
