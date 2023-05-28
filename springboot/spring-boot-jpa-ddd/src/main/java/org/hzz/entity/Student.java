package org.hzz.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column
    private String id;

    @Column
    private String name;

    @Column
    private String grade;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_hobbies", joinColumns = @JoinColumn(name = "student_id"))
    private Set<String> hobbies = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_book", joinColumns = @JoinColumn(name = "student_id"))
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void addHobby(String hobby) {
        hobbies.add(hobby);
    }
}
