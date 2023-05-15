package org.hzz.collection.combine;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;

/**
 * 内部根据价格来排序
 */
@Data
@AllArgsConstructor
public class Book implements Comparable<Book> {
    private String name;
    private Double price;
    private Integer sales; // 销量
    @Override
    public int compareTo(Book book) {
        return this.price.compareTo(book.price);
    }
}
