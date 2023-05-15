package org.hzz.collection.combine;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class CombineTest {
    public static void main(String[] args) {
        Book[] books = new Book[]{
            new Book("Java", 100.0, 1000),
                new Book("Python", 200.0, 2000),
                new Book("C++", 50.0, 2000),
                new Book("C", 50.0, 3000),
                new Book("C#", 350.0, 2500),
                new Book("PHP", 350.0, 100),
        };

        Comparator<Book> bookComparator = (book1,book2)->{
            // 先根据销量，如果销量相同再根据价格
            if (book1.getSales().equals(book2.getSales())) {
                return book1.getPrice().compareTo(book2.getPrice());
            } else {
                return book1.getSales().compareTo(book2.getSales());
            }
        };

        // 内部根据价格
        // 外部根据销量
        Arrays.sort(books, bookComparator);
        Stream.of(books).forEach(System.out::println);
    }
}
/**
 * Book(name=PHP, price=350.0, sales=100)
 * Book(name=Java, price=100.0, sales=1000)
 * Book(name=Python, price=200.0, sales=2000)
 * Book(name=C++, price=50.0, sales=2000)
 * Book(name=C#, price=350.0, sales=2500)
 * Book(name=C, price=50.0, sales=3000)
 */