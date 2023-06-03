package org.hzz.controller;

import org.hzz.data.BookDto;
import org.hzz.ports.api.BookServicePort;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController implements BookApi{

    private final BookServicePort bookServicePort;

    public BookController(BookServicePort bookServicePort) {
        this.bookServicePort = bookServicePort;
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        return bookServicePort.addBook(bookDto);
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        return bookServicePort.updateBook(bookDto);
    }

    @Override
    public BookDto getBookByID(long id) {
        return bookServicePort.getBookById(id);
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookServicePort.getBooks();
    }

    @Override
    public void deleteBookByID(long id) {
        bookServicePort.deleteBookById(id);
    }
}
